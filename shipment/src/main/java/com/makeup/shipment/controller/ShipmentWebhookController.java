package com.makeup.shipment.controller;

import com.makeup.shipment.dto.CourierWebhook;
import com.makeup.shipment.service.ShipmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipments/webhook")
public class ShipmentWebhookController {

    private final ShipmentService shipmentService;

    public ShipmentWebhookController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public void webhook(
            @RequestBody CourierWebhook payload
    ) {
        shipmentService.deliver(payload.getShipmentId());
    }
}