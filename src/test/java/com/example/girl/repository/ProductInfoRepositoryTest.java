package com.example.girl.repository;

import com.example.girl.domain.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    public ProductInfoRepository productInfoRepository;

    @Test
    public void test1(){
        ProductInfo one = productInfoRepository.findOne("1");
        System.out.println(one);
    }

}