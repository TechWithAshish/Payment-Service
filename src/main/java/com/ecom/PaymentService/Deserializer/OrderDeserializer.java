package com.ecom.PaymentService.Deserializer;

import com.ecom.PaymentService.Entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class OrderDeserializer implements Deserializer<Order> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Order deserialize(String s, byte[] bytes) {
        try{
            return objectMapper.readValue(bytes, Order.class);
        }
        catch (IOException exception){
            throw new RuntimeException("Failed to deserialize Order", exception);
        }
    }
}
