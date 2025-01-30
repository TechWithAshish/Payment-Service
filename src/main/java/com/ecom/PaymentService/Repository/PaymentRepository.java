package com.ecom.PaymentService.Repository;

import com.ecom.PaymentService.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public Payment findByOrderId(int orderId);
    public List<Payment> findByCustomerId(int customerId);
}
