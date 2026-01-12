package com.makeup.shipment.consumer;

import com.makeup.shipment.dto.DomainEvent;
import com.makeup.shipment.dto.PaymentSucceededEvent;
import com.makeup.shipment.service.ShipmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    private final ShipmentService shipmentService;

    public PaymentEventConsumer(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @KafkaListener(topics = "payment.events", groupId = "shipment-service")
    public void onPaymentEvent(
            DomainEvent<PaymentSucceededEvent> event,
            Acknowledgment ack) {

        if ("PaymentSucceeded".equals(event.getEventType())) {
            shipmentService.createShipment(
                    event.getData().getOrderId(),
                    event.getData().getOrderNo()
            );
        }

        ack.acknowledge();
    }
}