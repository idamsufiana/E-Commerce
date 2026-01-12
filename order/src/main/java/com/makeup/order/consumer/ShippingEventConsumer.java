package com.makeup.order.consumer;

import com.makeup.order.model.DomainEvent;
import com.makeup.order.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class ShippingEventConsumer {

    private final OrderService orderService;

    public ShippingEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "shipping.events", groupId = "order-service")
    public void onShippingEvent(DomainEvent<?> event, Acknowledgment ack) {

        if ("OrderShipped".equals(event.getEventType())) {
            orderService.markShipped(
                    Long.valueOf(event.getAggregateId())
            );
        }

        ack.acknowledge();
    }
}