package com.example.girl.DTO;

import com.example.girl.domain.OrderDetail;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    @Id
    /**订单id*/
    private String orderId;

    /**买家姓名*/
    private String buyerName;

    /**买家手机号码*/
    private String buyerPhone;

    /**买家送货地址*/
    private String buyerAddress;

    /**买家微信openid*/
    private String buyerOpenid;

    /**订单总额*/
    private BigDecimal orderAmount;

    /**订单状态*/
    private Integer orderStatus ;

    /**支付状态*/
    private Integer payStatus ;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    private List<OrderDetail> orderDetails;
}
