package com.makeup.shipment.service;

import com.makeup.shipment.model.Shipment;
import com.makeup.shipment.model.ShipmentStatus;
import com.makeup.shipment.producer.ShipmentEventPublisher;
import com.makeup.shipment.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;
    private final ShipmentEventPublisher publisher;

    public ShipmentService(ShipmentRepository repository, ShipmentEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public void createShipment(Long orderId, String orderNo) {

        if (repository.findByOrderId(orderId).isPresent()) {
            return; // idempotent
        }

        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setOrderNo(orderNo);
        shipment.setStatus(ShipmentStatus.PENDING);

        repository.save(shipment);
    }

    @Transactional
    public void ship(Long shipmentId, String trackingNo) {

        Shipment shipment = repository.findById(shipmentId)
                .orElseThrow();

        shipment.setTrackingNo(trackingNo);
        shipment.setStatus(ShipmentStatus.SHIPPED);
        shipment.setShippedAt(LocalDateTime.now());

        repository.save(shipment);

        publisher.publishOrderShipped(shipment);
    }

    @Transactional
    public void deliver(Long shipmentId) {

        Shipment shipment = repository.findById(shipmentId)
                .orElseThrow();

        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDeliveredAt(LocalDateTime.now());

        repository.save(shipment);

        publisher.publishOrderDelivered(shipment);
    }
}