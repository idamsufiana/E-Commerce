package com.makeup.order.producer;

import com.makeup.order.dto.Item;
import com.makeup.order.model.DomainEvent;
import com.makeup.order.model.Order;
import com.makeup.order.model.OrderCanceledEvent;
import com.makeup.order.model.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(Order order, List<Item> items) {

        DomainEvent<OrderCreatedEvent> event =
                DomainEvent.<OrderCreatedEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("OrderCreated")
                        .aggregateType("Order")
                        .aggregateId(order.getId().toString())
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(new OrderCreatedEvent(order.getId(), items))
                        .build();

        kafkaTemplate.send("order.events", event.getAggregateId(), event);
    }

    public void publishOrderCanceled(Long orderId, String reason) {

        DomainEvent<OrderCanceledEvent> event =
                DomainEvent.<OrderCanceledEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("OrderCanceled")
                        .aggregateType("Order")
                        .aggregateId(orderId.toString())
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(new OrderCanceledEvent(orderId, reason))
                        .build();

        kafkaTemplate.send("order.events", orderId.toString(), event);
    }
}
