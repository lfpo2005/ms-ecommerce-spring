package dev.luisoliveira.msproductmanagement.configurations.kafka.publishers;

//@Service
public class ProductProducer {

//    private final Producer<String, String> producer;
//
//    @Value("${kafka.topic.name}")
//    private String topicName;
//
//    @Autowired
//    public ProductProducer(Producer<String, String> producer) {
//        this.producer = producer;
//    }
//
//    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            //userEventDto.setActionType(actionType.toString());
//            String userEventDtoJson = objectMapper.writeValueAsString(userEventDto);
//            producer.send(new ProducerRecord<>(topicName, "", userEventDtoJson));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}