package com.makeup.payment.dto;

import lombok.Data;

@Data
public class PaymentWebhookRequest {
    private Long orderId;
    private String status;
    private String reason;
}
