package com.makeup.payment.service;

import com.makeup.payment.model.Payment;
import com.makeup.payment.model.PaymentStatus;
import com.makeup.payment.producer.PaymentEventPublisher;
import com.makeup.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final PaymentEventPublisher publisher;

    public PaymentService(PaymentRepository repo, PaymentEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Transactional
    public void createPayment(Long orderId) {

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setStatus(PaymentStatus.PENDING);

        repo.save(payment);
    }

    @Transactional
    public void markSuccess(Long orderId) {
        Payment payment = repo.findByOrderId(orderId).orElseThrow();
        payment.setStatus(PaymentStatus.SUCCESS);
        repo.save(payment);

        publisher.publishPaymentSucceeded(orderId);
    }
}