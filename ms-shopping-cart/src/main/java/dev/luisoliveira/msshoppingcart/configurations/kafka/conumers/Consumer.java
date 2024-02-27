package dev.luisoliveira.msshoppingcart.configurations.kafka.conumers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.luisoliveira.msshoppingcart.dtos.ProductEventDto;
import dev.luisoliveira.msshoppingcart.dtos.UserEventDto;
import dev.luisoliveira.msshoppingcart.service.ProductCartService;
import dev.luisoliveira.msshoppingcart.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    UserService userService;
    @Autowired
    ProductCartService productCartService;

    @KafkaListener(topics = {"stg_topic_new_user"}, groupId = "group-cart")
    public void listenUserEvent(UserEventDto userEventDto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        var userModel = objectMapper.convertValue(userEventDto, UserEventDto.class).convertToUserModel();
        switch (userEventDto.getActionType()) {
            case CREATE:
            case UPDATE:
                userService.save(userModel);
                break;
            case DELETE:
                userService.delete(userEventDto.getUserId());
                break;
        }
    }

    @KafkaListener(topics = {"stg_topic_products"}, groupId = "group-cart")
    public void listenProductEvent(ConsumerRecord<String, JsonNode> record) throws Exception {
        JsonNode jsonNode = record.value();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        ProductEventDto productCart = objectMapper.treeToValue(jsonNode, ProductEventDto.class);
        switch (productCart.getActionType()) {
            case CREATE:
            case UPDATE:
                productCartService.save(productCart.convertToProductCart());
                break;
            case DELETE:
                productCartService.delete(productCart.getProductId());
                break;
        }
    }
}