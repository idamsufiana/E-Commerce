package com.makeup.catalog.consumer;

import com.makeup.catalog.dto.DomainEvent;
import com.makeup.catalog.dto.OrderCreatedEvent;
import com.makeup.catalog.producer.CatalogEventPublisher;
import com.makeup.catalog.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final InventoryService inventoryService;
    private final CatalogEventPublisher publisher;

    public OrderEventConsumer(InventoryService inventoryService, CatalogEventPublisher publisher) {
        this.inventoryService = inventoryService;
        this.publisher = publisher;
    }

    @KafkaListener(topics = "order.events", groupId = "catalog-service")
    public void onOrderCreated(
            DomainEvent<OrderCreatedEvent> event,
            Acknowledgment ack) {

        if (!"OrderCreated".equals(event.getEventType())) {
            ack.acknowledge();
            return;
        }

        try {
            inventoryService.reserve(event.getData().getItems());
            publisher.publishStockReserved(event.getAggregateId());
        } catch (Exception e) {
            publisher.publishStockRejected(
                    event.getAggregateId(),
                    e.getMessage()
            );
        }

        ack.acknowledge();
    }
}