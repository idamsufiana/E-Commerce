package com.makeup.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutRequest {

    private BigDecimal total;

    private List<Item> items;

}