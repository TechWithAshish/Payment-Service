package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.Order;
import com.ecom.PaymentService.Entity.Payment;
import com.ecom.PaymentService.Entity.PaymentOutbox;
import com.ecom.PaymentService.Repository.PaymentOutboxRepository;
import com.ecom.PaymentService.Repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final PaymentOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentServiceImpl(PaymentOutboxRepository outboxRepository, PaymentRepository paymentRepository, ObjectMapper objectMapper){
        this.outboxRepository = outboxRepository;
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }
    @Override
    @Transactional
    public void makePayment(Order order) throws JsonProcessingException {
        Payment payment = Payment
                .builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .quantity(order.getQuantity())
                .amount(order.getPrice())
                .status(PaymentStatusAPI.getPaymentStatus())
                .build();
        payment = paymentRepository.save(payment);
        // let's make outbox for this event....
        String payload = objectMapper.writeValueAsString(payment);
        PaymentOutbox paymentOutbox = PaymentOutbox
                .builder()
                .topic("PaymentStatus")
                .payload(payload)
                .build();
        outboxRepository.save(paymentOutbox);
    }

    @Override
    public Payment getPaymentByOrderId(int orderId) {
        return null;
    }

    @Override
    public Payment getPaymentByPaymentId(int paymentId) {
        return null;
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(int customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }
}
