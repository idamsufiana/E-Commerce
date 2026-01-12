package com.makeup.order.consumer;

import com.makeup.order.model.DomainEvent;
import com.makeup.order.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class CatalogEventConsumer {

    private final OrderService orderService;

    public CatalogEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "catalog.events", groupId = "order-service")
    public void onCatalogEvent(
            DomainEvent<?> event,
            Acknowledgment ack) {

        switch (event.getEventType()) {

            case "StockReserved" -> {
                // Optional: update order to AWAITING_PAYMENT
            }

            case "StockRejected" -> {
                orderService.cancelOrder(
                        Long.valueOf(event.getAggregateId()),
                        "STOCK_NOT_AVAILABLE"
                );
            }
        }

        ack.acknowledge();
    }
}
