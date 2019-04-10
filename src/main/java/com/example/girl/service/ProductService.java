package com.example.girl.service;

import com.example.girl.DTO.CartDto;
import com.example.girl.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(CartDto cartDto);

    //减库存
    void decreaseStock(CartDto cartDto);

    //上架

    //下架
}
