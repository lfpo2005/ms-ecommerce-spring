package dev.msusermanagement.configurations.kafka.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.msusermanagement.dtos.UserEventDto;
import dev.msusermanagement.enums.ActionType;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class UserProducer {

    private final Producer<String, String> producer;

    private String topicName;
    private String kafkaUrl;

    @Autowired
    public UserProducer(@Value("${kafka.topic.name}") String topicName,
                        @Value("${spring.kafka.bootstrap-servers}") String kafkaUrl) {
        this.topicName = topicName;
        this.kafkaUrl = kafkaUrl;

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userEventDto.setActionType(actionType.toString());
            String userEventDtoJson = objectMapper.writeValueAsString(userEventDto);
            producer.send(new ProducerRecord<>(topicName, "", userEventDtoJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}