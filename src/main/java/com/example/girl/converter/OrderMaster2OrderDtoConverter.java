package com.example.girl.converter;

import com.example.girl.DTO.OrderDTO;
import com.example.girl.domain.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderMaster2OrderDtoConverter{
    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasters){
        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasters) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderDTO,orderMaster);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }
}
