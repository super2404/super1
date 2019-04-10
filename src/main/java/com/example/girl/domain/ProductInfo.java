package com.example.girl.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
    public String productId;

    public String productName;

    public BigDecimal productPrice;

    public Integer productStock;

    public String productDescription;

    public String productIcon;

    public Integer categoryType;

    /**
     * 商品状态，0 上架，1 下架
     */
    public Integer productStatus;

    public Date createTime;

    public Date updateTime;

}
