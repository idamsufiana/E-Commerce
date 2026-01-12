package com.makeup.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class PaymentFailedEvent {

    private Long orderId;

    private List<Item> items;

}
