package com.makeup.payment.consumer;

import com.makeup.payment.dto.DomainEvent;
import com.makeup.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final PaymentService paymentService;

    public OrderEventConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order.events", groupId = "payment-service")
    public void onOrderCreated(DomainEvent<?> event, Acknowledgment ack) {

        if ("OrderCreated".equals(event.getEventType())) {
            paymentService.createPayment(
                    Long.valueOf(event.getAggregateId())
            );
        }

        ack.acknowledge();
    }
}
