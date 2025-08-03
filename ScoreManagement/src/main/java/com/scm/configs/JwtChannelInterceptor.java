package com.scm.configs;

import com.scm.utils.JwtUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class JwtChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                throw new RuntimeException("Missing JWT");
            }

            token = token.substring(7); // Bỏ "Bearer "

            try {
                String username = JwtUtils.validateTokenAndGetUsername(token);
                if (username == null) {
                    throw new RuntimeException("Invalid JWT");
                }
                accessor.setUser(new UsernamePasswordAuthenticationToken(username, null));
            } catch (Exception e) {
                throw new RuntimeException("JWT validation failed", e);
            }
        }
        return message;
    }
}