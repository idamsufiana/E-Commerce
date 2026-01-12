package com.makeup.order.consumer;

import com.makeup.order.model.DomainEvent;
import com.makeup.order.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private final OrderService orderService;

    public PaymentEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "payment.events", groupId = "order-service")
    public void onPaymentEvent(DomainEvent<?> event, Acknowledgment ack) {

        if ("PaymentSucceeded".equals(event.getEventType())) {
            orderService.markPaid(
                    Long.valueOf(event.getAggregateId())
            );
        }

        ack.acknowledge();
    }
}