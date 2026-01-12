package com.makeup.payment.producer;

import com.makeup.payment.dto.DomainEvent;
import com.makeup.payment.dto.PaymentExpiredEvent;
import com.makeup.payment.dto.PaymentFailedEvent;
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

    public void publishPaymentFailed(Long orderId, String reason) {

        DomainEvent<PaymentFailedEvent> event =
                DomainEvent.<PaymentFailedEvent>builder()
                        .eventType("PaymentFailed")
                        .aggregateType("Order")
                        .aggregateId(orderId.toString())
                        .data(new PaymentFailedEvent(orderId, reason))
                        .build();

        kafka.send("payment.events", orderId.toString(), event);
    }
    public void publishPaymentExpired(Long orderId) {

        DomainEvent<PaymentExpiredEvent> event =
                DomainEvent.<PaymentExpiredEvent>builder()
                        .eventType("PaymentExpired")
                        .aggregateType("Order")
                        .aggregateId(orderId.toString())
                        .data(new PaymentExpiredEvent(orderId))
                        .build();

        kafka.send("payment.events", orderId.toString(), event);
    }
}
