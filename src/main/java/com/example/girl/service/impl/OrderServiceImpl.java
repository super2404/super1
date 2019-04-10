package com.example.girl.service.impl;

import com.example.girl.DTO.CartDto;
import com.example.girl.DTO.OrderDTO;
import com.example.girl.Enum.OrderStatusEnum;
import com.example.girl.Enum.PayStatusEnum;
import com.example.girl.Enum.ResultEnum;
import com.example.girl.converter.OrderMaster2OrderDtoConverter;
import com.example.girl.domain.OrderDetail;
import com.example.girl.domain.OrderMaster;
import com.example.girl.domain.ProductInfo;
import com.example.girl.exception.SellException;
import com.example.girl.repository.OrderDetailRepository;
import com.example.girl.repository.OrderMasterRepository;
import com.example.girl.repository.ProductInfoRepository;
import com.example.girl.service.OrderService;
import com.example.girl.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository repository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ProductServiceImpl productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws SellException {
        /**
         * 1.查询商品
         * 2.计算总金额
         *      (单价需要从数据库中查询，不能从前端获取)
         * 3.封装order_master和order_detail
         * 4.扣库存
         */
        //1.查询商品
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDetails) {
            ProductInfo productInfo = productInfoRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总金额(单价需要从数据库中查询，不能从前端获取)
            orderAmount = orderAmount.add(productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
            //4.扣库存
            CartDto cartDto = new CartDto(productInfo.getProductId(), orderDetail.getProductQuantity());
            productService.decreaseStock(cartDto);
            //3.封装order_detail
            productInfo.setProductStock((productInfo.getProductStock() - orderDetail.getProductQuantity()));
            BeanUtils.copyProperties(orderDetail, productInfo);
            orderDetail.setDetailId(KeyUtil.getKey());
            orderDetailRepository.save(orderDetail);
        }
        //封装order_master
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderId(KeyUtil.getKey());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        repository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = repository.findOne(orderId);
        if(orderMaster==null){
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = repository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOS = OrderMaster2OrderDtoConverter.converter(page.getContent());
        Page<OrderDTO> pageOrderDto = new PageImpl<>(orderDTOS,pageable,page.getTotalElements());
        return pageOrderDto;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1.判断订单状态（已完结不能取消）
        if(!orderDTO.getPayStatus().equals(OrderStatusEnum.NEW)){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_FAIL);
        }
        //2.改变订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        //3.加库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetails())){
            log.error("【取消订单】商品列表不存在,orderDTO={}"+orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            CartDto cartDto = new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity());
            productService.increaseStock(cartDto);
        }
        //4.已支付状态就退款，改变支付状态
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            //TODO
        }
        //5.保存orderMaster
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        repository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        return null;
    }
}
