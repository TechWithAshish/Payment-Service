package com.ecom.PaymentService.Deserializer;


import com.ecom.PaymentService.Entity.Inventory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class InventoryDeserializer implements Deserializer<Inventory> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Inventory deserialize(String s, byte[] bytes) {
        try{
            return objectMapper.readValue(bytes, Inventory.class);
        }
        catch (IOException exception){
            throw  new RuntimeException("Error while deserializing data Inventory class", exception);
        }
    }
}
