package com.makeup.order.controller;

import com.makeup.order.dto.CheckoutRequest;
import com.makeup.order.model.Order;
import com.makeup.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * USER checkout
     */
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @RequestBody CheckoutRequest request
    ) {
        Order order = orderService.checkout(
                1l,
                request.getTotal(),
                request
        );

        return ResponseEntity.ok(order);
    }

    /**
     * USER cancel order (manual)
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancel(
            @PathVariable Long orderId
    ) {
        orderService.cancelOrder(orderId, "USER_CANCELED");
        return ResponseEntity.ok().build();
    }
}