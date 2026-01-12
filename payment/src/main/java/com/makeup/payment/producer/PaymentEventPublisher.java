package com.makeup.payment.producer;

import com.makeup.payment.dto.DomainEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public PaymentEventPublisher(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void publishPaymentSucceeded(Long orderId) {

        DomainEvent<?> event =
                DomainEvent.builder()
                        .eventType("PaymentSucceeded")
                        .aggregateType("Order")
                        .aggregateId(orderId.toString())
                        .build();

        kafka.send("payment.events", orderId.toString(), event);
    }
}
