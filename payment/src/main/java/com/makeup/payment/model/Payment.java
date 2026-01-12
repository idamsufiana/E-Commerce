package com.makeup.payment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private Long orderId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String failureReason;

    private String providerRef;
}