package com.ecom.PaymentService.Kafka;

import com.ecom.PaymentService.Entity.Inventory;
import com.ecom.PaymentService.Service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryConsumer {
    private final PaymentService paymentService;

    @Autowired
    public InventoryConsumer(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "InventoryReserved", groupId = "group-2", containerFactory = "inventoryKafkaListenerContainerFactory")
    public void inventoryEventConsumer(Inventory inventory) throws JsonProcessingException {
        log.info("Listing event from InventoryReserved topics and making payment for inventory: {}", inventory.toString());
        paymentService.makePayment(inventory);
    }

    @KafkaListener(topics = "InventoryReservationFailed", groupId = "group-2", containerFactory = "inventoryKafkaListenerContainerFactory")
    public void inventoryFailedEventConsumer(Inventory inventory) throws JsonProcessingException {
        log.info("Listing event from InventoryReservationFailed topics and making payment for inventory: {}", inventory.toString());
        // make this payment as cancel and send status to order id as well
        paymentService.makePaymentCancel(inventory);
    }
}
