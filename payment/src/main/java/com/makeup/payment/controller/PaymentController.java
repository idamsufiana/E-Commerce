package com.makeup.payment.controller;

import com.makeup.payment.dto.PaymentWebhookRequest;
import com.makeup.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments/webhook")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Void> callback(
            @RequestBody PaymentWebhookRequest payload
    ) {
        if ("SUCCESS".equals(payload.getStatus())) {
            paymentService.markSuccess(payload.getOrderId());
        }
        return ResponseEntity.ok().build();
    }
}