package com.makeup.shipment.controller;

import com.makeup.shipment.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/{id}/ship")
    public void ship(
            @PathVariable Long id,
            @RequestParam String trackingNo
    ) {
        shipmentService.ship(id, trackingNo);
    }
}