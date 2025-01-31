package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.Inventory;
import com.ecom.PaymentService.Entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PaymentService {
    public void makePayment(Inventory inventory) throws JsonProcessingException;
    public Payment getPaymentByOrderId(int orderId);
    public Payment getPaymentByPaymentId(int paymentId);
    public List<Payment> getPaymentsByCustomerId(int customerId);
    public void makePaymentCancel(Inventory inventory) throws JsonProcessingException;
}
