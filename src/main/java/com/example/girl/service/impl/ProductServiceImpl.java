package com.example.girl.service.impl;

import com.example.girl.DTO.CartDto;
import com.example.girl.Enum.ProductStatusEnum;
import com.example.girl.Enum.ResultEnum;
import com.example.girl.domain.ProductInfo;
import com.example.girl.exception.SellException;
import com.example.girl.repository.ProductInfoRepository;
import com.example.girl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductInfoRepository repository;
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    /**
     * 查询所有全部上架的商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(CartDto cartDto) {
        ProductInfo productInfo = repository.findOne(cartDto.getProductId());
        productInfo.setProductStock(productInfo.getProductStock()+cartDto.getProductQuantity());
        repository.save(productInfo);
    }

    @Override
    public void decreaseStock(CartDto cartDto) {
        ProductInfo productInfo = repository.findOne(cartDto.getProductId());
        if(productInfo.getProductStock()-cartDto.getProductQuantity()<=0){
            throw new SellException(ResultEnum.LACK_OF_STACK);
        }
        productInfo.setProductStock(productInfo.getProductStock()-cartDto.getProductQuantity());
        repository.save(productInfo);
    }
}
