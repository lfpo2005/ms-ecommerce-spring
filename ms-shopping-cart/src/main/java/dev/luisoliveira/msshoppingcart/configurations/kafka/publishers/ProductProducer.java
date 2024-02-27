package dev.luisoliveira.msshoppingcart.configurations.kafka.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class ProductProducer {

/*    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishProductEvent(ProductEventDto productEventDto, ActionType actionType) {
        ObjectMapper objectMapper = new ObjectMapper();
        String productEventDtoJson;
        try {
            productEventDtoJson = objectMapper.writeValueAsString(productEventDto);
        } catch (JsonProcessingException e) {
             throw new RuntimeException("Erro ao processar JSON", e);
        }

        kafkaTemplate.send("stg_topic_products", actionType.toString(), productEventDtoJson);
    }*/
}