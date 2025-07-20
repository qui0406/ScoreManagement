//package com.scm.services.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scm.dto.requests.MessageRequest;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailConsumer {
//
//    @Value("${sendgrid.api-key}")
//    private String sendGridApiKey;
//
//    @Value("${sendgrid.sender-email}")
//    private String senderEmail;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @KafkaListener(topics = "email-topic", groupId = "email-group")
//    public void consume(ConsumerRecord<String, String> record) {
//        try {
//            MessageRequest messageRequest = objectMapper.readValue(record.value(), MessageRequest.class);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}