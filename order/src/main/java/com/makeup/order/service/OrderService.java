package com.makeup.order.service;

import com.makeup.order.dto.CheckoutRequest;
import com.makeup.order.model.Order;
import com.makeup.order.model.OrderCreatedEvent;
import com.makeup.order.model.OrderStatus;
import com.makeup.order.producer.OrderEventPublisher;
import com.makeup.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher publisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    @Transactional
    public Order checkout(Long userId, BigDecimal total, CheckoutRequest items) {

        Order order = Order.builder()
                .orderNo("ORD-" + System.currentTimeMillis())
                .userId(userId)
                .status(OrderStatus.CREATED)
                .grandTotal(total)
                .build();

        orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setItems(items.getItems());
        publisher.publishOrderCreated(order, orderCreatedEvent.getItems());
        return order;
    }

    @Transactional
    public void cancelOrder(Long orderId, String reason) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        if (order.getStatus() == OrderStatus.CANCELED) return;

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        publisher.publishOrderCanceled(orderId, reason);
    }

    @Transactional
    public void markPaid(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Transactional
    public void markShipped(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);
    }
}
