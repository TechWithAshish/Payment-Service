package com.ecom.PaymentService.Kafka;

import com.ecom.PaymentService.Entity.Order;
import com.ecom.PaymentService.Service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {
    private final PaymentService paymentService;

    @Autowired
    public OrderConsumer(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "Orders", groupId = "group-2", containerFactory = "orderKafkaListenerContainerFactory")
    public void orderEventConsumer(Order order) throws JsonProcessingException {
        log.info("Listing event from Orders topics and making payment for Order: {}", order.toString());
        paymentService.makePayment(order);
    }
}
