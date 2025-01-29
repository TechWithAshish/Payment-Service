package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.Order;
import com.ecom.PaymentService.Entity.Payment;

public interface PaymentService {
    public void makePayment(Order order);
    public Payment getPaymentByOrderId(int orderId);
    public Payment getPaymentByPaymentId(int paymentId);
}
