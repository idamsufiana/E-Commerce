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
        switch (payload.getStatus()) {
            case "SUCCESS" -> paymentService.markSuccess(payload.getOrderId());
            case "FAILED"  -> paymentService.markFailed(payload.getOrderId(), payload.getReason());
            case "EXPIRED" -> paymentService.markExpired(payload.getOrderId());
            case "PENDING" -> {
                // optional: log only
            }
            default -> {
                // unknown status → log
            }
        }
        return ResponseEntity.ok().build();
    }
}