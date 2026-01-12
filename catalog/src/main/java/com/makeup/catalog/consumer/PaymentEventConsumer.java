package com.makeup.catalog.consumer;

import com.makeup.catalog.dto.DomainEvent;
import com.makeup.catalog.dto.PaymentFailedEvent;
import com.makeup.catalog.dto.PaymentSucceededEvent;
import com.makeup.catalog.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private final InventoryService inventoryService;

    public PaymentEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /* ========================
       PAYMENT SUCCESS
       ======================== */
    @KafkaListener(
            topics = "payment.events",
            groupId = "catalog-service"
    )
    public void onPaymentSucceeded(
            DomainEvent<PaymentSucceededEvent> event,
            Acknowledgment ack
    ) {
        if (!"PaymentSucceeded".equals(event.getEventType())) {
            ack.acknowledge();
            return;
        }

        inventoryService.consume(event.getData().getItems());
        ack.acknowledge();
    }

    /* ========================
       PAYMENT FAILED
       ======================== */
    @KafkaListener(
            topics = "payment.events",
            groupId = "catalog-service"
    )
    public void onPaymentFailed(
            DomainEvent<PaymentFailedEvent> event,
            Acknowledgment ack
    ) {
        if (!"PaymentFailed".equals(event.getEventType())) {
            ack.acknowledge();
            return;
        }

        inventoryService.release(event.getData().getItems());
        ack.acknowledge();
    }
}