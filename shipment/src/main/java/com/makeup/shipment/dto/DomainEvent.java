package com.makeup.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainEvent<T> {
    private String eventId;        // UUID
    private String eventType;      // OrderCreated, PaymentSucceeded, ...
    private Instant occurredAt;
    private String aggregateType;  // Order
    private String aggregateId;    // orderId as string
    private int version;
    private T data;
}
