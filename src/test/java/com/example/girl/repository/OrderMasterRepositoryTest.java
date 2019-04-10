package com.example.girl.repository;

import com.example.girl.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private final String OpenId = "111111";

    @Test
    public  void testOrderQuery(){
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(OpenId, pageRequest);
        Assert.assertNotNull(orderMasters.getSize());
    }
}