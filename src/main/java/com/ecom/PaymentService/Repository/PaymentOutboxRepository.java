package com.ecom.PaymentService.Repository;

import com.ecom.PaymentService.Entity.PaymentOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOutboxRepository extends JpaRepository<PaymentOutbox, Integer> {
}
