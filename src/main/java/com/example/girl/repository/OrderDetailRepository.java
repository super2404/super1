package com.example.girl.repository;

import com.example.girl.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    /**
     * 根据订单id获取订单详情
     * @param orderId
     * @return
     */
    public List<OrderDetail> findByOrderId(String orderId);
}
