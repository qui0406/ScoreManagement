//package com.scm.services.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scm.dto.requests.MessageRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class EmailProducer {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//    @Autowired
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    public EmailProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendEmailRequest(MessageRequest messageRequest) {
//        try {
//            String json = objectMapper.writeValueAsString(messageRequest);
//            kafkaTemplate.send("email-topic", json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
