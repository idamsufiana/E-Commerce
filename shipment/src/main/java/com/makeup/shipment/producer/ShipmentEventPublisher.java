package com.makeup.shipment.producer;

import com.makeup.shipment.dto.DomainEvent;
import com.makeup.shipment.dto.OrderDeliveredEvent;
import com.makeup.shipment.dto.OrderShippedEvent;
import com.makeup.shipment.model.Shipment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ShipmentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ShipmentEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderShipped(Shipment shipment) {

        DomainEvent<OrderShippedEvent> event =
                DomainEvent.<OrderShippedEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("OrderShipped")
                        .aggregateType("Order")
                        .aggregateId(shipment.getOrderId().toString())
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(new OrderShippedEvent(
                                shipment.getOrderId(),
                                shipment.getOrderNo(),
                                shipment.getTrackingNo()
                        ))
                        .build();

        kafkaTemplate.send("shipping.events",
                shipment.getOrderId().toString(), event);
    }

    public void publishOrderDelivered(Shipment shipment) {

        DomainEvent<OrderDeliveredEvent> event =
                DomainEvent.<OrderDeliveredEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("OrderDelivered")
                        .aggregateType("Order")
                        .aggregateId(shipment.getOrderId().toString())
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(new OrderDeliveredEvent(
                                shipment.getOrderId(),
                                shipment.getOrderNo()
                        ))
                        .build();

        kafkaTemplate.send("shipping.events",
                shipment.getOrderId().toString(), event);
    }
}