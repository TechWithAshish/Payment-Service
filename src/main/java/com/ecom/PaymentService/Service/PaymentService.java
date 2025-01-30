package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.Order;
import com.ecom.PaymentService.Entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PaymentService {
    public void makePayment(Order order) throws JsonProcessingException;
    public Payment getPaymentByOrderId(int orderId);
    public Payment getPaymentByPaymentId(int paymentId);
    public List<Payment> getPaymentsByCustomerId(int customerId);
}
