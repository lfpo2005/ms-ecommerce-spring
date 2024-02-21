package dev.luisoliveira.msproductmanagement.configurations.kafka.conumers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.luisoliveira.msproductmanagement.dtos.UserEventDto;
import dev.luisoliveira.msproductmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private final UserService userService;

    @Autowired
    public UserConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "${kafka.listener.topics.name}", groupId = "${kafka.topic.groupId}")
    public void consume(UserEventDto userEventDto) throws Exception {
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


/*    @KafkaListener(topics = "${kafka.listener.topics.name}", groupId = "${kafka.topic.groupId}")
    public void consume(UserEventDto userEventDto) {
        var userModel = userEventDto.convertToUserModel();
        switch (userEventDto.getActionType()) {
            case CREATE:
            case UPDATE:
                userService.save(userModel);
                break;
            case DELETE:
                userService.delete(userEventDto.getUserId());
                break;
        }
    }*/
    }
}