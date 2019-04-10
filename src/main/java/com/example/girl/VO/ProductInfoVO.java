package com.example.girl.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVO {

    @JsonProperty("id")
    public String productId;

    @JsonProperty("name")
    public String productName;

    @JsonProperty("price")
    public BigDecimal productPrice;

    @JsonProperty("description")
    public String productDescription;

    @JsonProperty("icon")
    public String productIcon;
}
