package com.makeup.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderShippedEvent {
    private Long orderId;
    private String orderNo;
    private String trackingNo;
}