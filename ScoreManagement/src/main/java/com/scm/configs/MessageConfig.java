//package com.scm.configs;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//public class MessageConfig {
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
//        // Có thể cấu hình thêm host, port, password ở đây nếu không dùng application.properties
//        return connectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        // Serializer cho key (String)
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//
//        // Serializer cho value (JSON)
//        Jackson2JsonRedisSerializer<Object> valueSerializer =
//                new Jackson2JsonRedisSerializer<>(Object.class);
//        template.setValueSerializer(valueSerializer);
//        template.setHashValueSerializer(valueSerializer);
//
//        return template;
//    }
//
//    @Bean
//    public MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        // Có thể cấu hình thêm retry, timeout...
//        return rabbitTemplate;
//    }
//}