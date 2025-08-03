//package com.scm.configs;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//
//public class RabbitMQConfig {
//    @Bean
//    public TopicExchange publicChatExchange() {
//        return new TopicExchange("public.chat.exchange");
//    }
//
//    @Bean
//    public TopicExchange privateChatExchange() {
//        return new TopicExchange("private.chat.exchange");
//    }
//
//    @Bean
//    public Queue publicChatQueue() {
//        return new Queue("public.chat.queue");
//    }
//
//    @Bean
//    public Binding publicChatBinding() {
//        return BindingBuilder.bind(publicChatQueue())
//                .to(publicChatExchange())
//                .with("public.chat.routingkey");
//    }
//
//    @Bean
//    public Queue privateChatQueue(String userId) {
//        return new Queue("private.chat.queue." + userId);
//    }
//
//    @Bean
//    public Binding privateChatBinding(String userId) {
//        return BindingBuilder.bind(privateChatQueue(userId))
//                .to(privateChatExchange())
//                .with("private.chat." + userId);
//    }
//}
