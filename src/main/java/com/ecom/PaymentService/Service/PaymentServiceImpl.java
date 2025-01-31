package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.Inventory;
import com.ecom.PaymentService.Entity.Payment;
import com.ecom.PaymentService.Entity.PaymentOutbox;
import com.ecom.PaymentService.Entity.PaymentStatus;
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
    public void makePayment(Inventory inventory) throws JsonProcessingException {
        Payment payment = Payment
                .builder()
                .orderId(inventory.getOrderId())
                .customerId(inventory.getCustomerId())
                .quantity(inventory.getQuantity())
                .productId(inventory.getProductId())
                .amount(inventory.getAmount())
                .status(PaymentStatusAPI.getPaymentStatus())
                .build();
        payment = paymentRepository.save(payment);
        // let's make outbox for this event....
        // now based on payment status it will go to different topics
        String payload = objectMapper.writeValueAsString(payment);
        PaymentOutbox paymentOutbox = PaymentOutbox
                .builder()
                .payload(payload)
                .build();
        if(payment.getStatus().equals("FAILED")){
            paymentOutbox.setTopic("PaymentFailed");
            outboxRepository.save(paymentOutbox);
        }
        paymentOutbox.setTopic("PaymentStatus");
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

    @Override
    public void makePaymentCancel(Inventory inventory) throws JsonProcessingException {
        Payment payment = Payment
                .builder()
                .orderId(inventory.getOrderId())
                .customerId(inventory.getCustomerId())
                .quantity(inventory.getQuantity())
                .productId(inventory.getProductId())
                .amount(inventory.getAmount())
                .status(PaymentStatus.CANCEL.toString())
                .build();
        payment = paymentRepository.save(payment);
        // let's make outbox for this event....
        // now based on payment status cancel it will go to PaymentStatus to update order status in order service...
        String payload = objectMapper.writeValueAsString(payment);
        PaymentOutbox paymentOutbox = PaymentOutbox
                .builder()
                .payload(payload)
                .topic("PaymentStatus")
                .build();
        paymentOutbox.setTopic("PaymentStatus");
        outboxRepository.save(paymentOutbox);
    }
}
