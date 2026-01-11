package com.makeup.catalog.producer;

import com.makeup.catalog.dto.DomainEvent;
import com.makeup.catalog.dto.StockRejectedEvent;
import com.makeup.catalog.dto.StockReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CatalogEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CatalogEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStockReserved(String orderId) {

        DomainEvent<StockReservedEvent> event =
                DomainEvent.<StockReservedEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("StockReserved")
                        .aggregateType("Order")
                        .aggregateId(orderId)
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(new StockReservedEvent(Long.valueOf(orderId)))
                        .build();

        kafkaTemplate.send("catalog.events", orderId, event);
    }

    public void publishStockRejected(String orderId, String reason) {

        DomainEvent<StockRejectedEvent> event =
                DomainEvent.<StockRejectedEvent>builder()
                        .eventId(UUID.randomUUID().toString())
                        .eventType("StockRejected")
                        .aggregateType("Order")
                        .aggregateId(orderId)
                        .occurredAt(Instant.now())
                        .version(1)
                        .data(
                                StockRejectedEvent.builder()
                                        .orderId(Long.valueOf(orderId))
                                        .reason(reason)
                                        .build()
                        )
                        .build();

        kafkaTemplate.send("catalog.events", orderId, event);
    }
}