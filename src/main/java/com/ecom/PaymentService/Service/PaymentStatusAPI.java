package com.ecom.PaymentService.Service;

import com.ecom.PaymentService.Entity.PaymentStatus;

import java.util.Random;

public class PaymentStatusAPI {
    public static String getPaymentStatus(){
        Random random = new Random();
        int num = random.nextInt(1, 8);
        return switch (num){
            case 2 -> PaymentStatus.FAILED.toString();
            case 3 -> PaymentStatus.PENDING.toString();
            default -> PaymentStatus.SUCCESS.toString();
        };
    }
}
