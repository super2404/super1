package com.example.girl.Enum;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    LACK_OF_STACK(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在") ,
    ORDERDETAIL_NOT_EXIST(13,"订单详细不存在"),
    ORDER_STATUS_FAIL(14,"订单状态不正确"),
    ORDER_DETAIL_EMPTY(15,"订单详情不存在");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
