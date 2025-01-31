package com.ecom.PaymentService.Kafka;

import com.ecom.PaymentService.Entity.Payment;
import com.ecom.PaymentService.Entity.PaymentOutbox;
import com.ecom.PaymentService.Repository.PaymentOutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class PaymentStatusProducer {
    private final PaymentOutboxRepository outboxRepository;
    private final KafkaProducerService<Payment> kafkaProducerService;
    private final ObjectMapper objectMapper;

    public PaymentStatusProducer(PaymentOutboxRepository outboxRepository, KafkaProducerService<Payment> kafkaProducerService, ObjectMapper objectMapper){
        this.outboxRepository = outboxRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = objectMapper;
    }
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void publishPaymentEventToKafka() throws JsonProcessingException {
        List<PaymentOutbox> paymentOutboxList = outboxRepository.findAll();
        for(PaymentOutbox paymentOutbox : paymentOutboxList){
            Payment payment = objectMapper.readValue(paymentOutbox.getPayload(), Payment.class);
            log.info("Publishing event for Topic: {} with payload : {}", paymentOutbox.getTopic(), paymentOutbox.getPayload());
            kafkaProducerService.sendMessage(paymentOutbox.getTopic(), payment);
            outboxRepository.delete(paymentOutbox);
        }
    }
}
