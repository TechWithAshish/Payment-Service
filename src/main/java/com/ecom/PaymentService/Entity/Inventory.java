package com.ecom.PaymentService.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Inventory {
    public int orderId;
    public int customerId;
    public int quantity;
    public double amount;
    public int productId;
}
