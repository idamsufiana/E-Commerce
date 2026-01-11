package com.makeup.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNo;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal grandTotal;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Order create(Long userId, BigDecimal total) {
        Order order = new Order();
        order.orderNo = "ORD-" + UUID.randomUUID();
        order.userId = userId;
        order.grandTotal = total;
        order.status = OrderStatus.CREATED;
        return order;
    }


    public void markPaid() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Cannot mark PAID from status " + status
            );
        }
        this.status = OrderStatus.PAID;
    }

    public void markShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException(
                    "Cannot mark SHIPPED from status " + status
            );
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void cancel(String reason) {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        if (status == OrderStatus.CANCELED) return;
        this.status = OrderStatus.CANCELED;
    }

}
