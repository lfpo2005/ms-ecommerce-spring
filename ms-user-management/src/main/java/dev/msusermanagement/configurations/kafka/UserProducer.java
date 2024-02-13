package dev.msusermanagement.configurations.kafka;

import dev.msusermanagement.models.UserModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;


@Service
public class UserProducer {

    private final Producer<String, String> producer;

    @Value("${kafka.topic.name}")
    private String topicName;
    @Value("${kafka.url}")
    private String serverUrl;

    public UserProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void sendUserDetails(UserModel userModel) {
        String userId = String.valueOf(userModel.getUserId());
        String fullName = userModel.getFullName();
        String userType = String.valueOf(userModel.getUserType());
        String email = userModel.getEmail();
        String cpf = userModel.getCpf();
        Boolean active = userModel.getActive();
        LocalDateTime updatedAt = LocalDateTime.now();

        String userRecord = String.format("{" +
                                            "\n\"userId\":\"%s\"," +
                                            "\n\"fullName\":\"%s\"," +
                                            "\n\"email\":\"%s\"," +
                                            "\n\"cpf\":\"%s\"," +
                                            "\n\"userType\":\"%s\"," +
                                            "\n\"active\":%s," +
                                            "\n\"updatedAt\":\"%s\"\n" +
                                            "}",
                                            userId, fullName, email, cpf, userType, active, updatedAt);
        producer.send(new ProducerRecord<>(topicName, userId, userRecord));
    }
}