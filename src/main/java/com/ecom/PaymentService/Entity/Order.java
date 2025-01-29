package com.ecom.PaymentService.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Order {
    public int orderId;
    public int customerId;
    public int productId;
    public int quantity;
    public double price;
    public String status;
    public int paymentId;

    public Order(){

    }
}
