package com.ecom.PaymentService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PaymentOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String topic;
    public String payload;
}
