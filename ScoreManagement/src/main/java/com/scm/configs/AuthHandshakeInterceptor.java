package com.scm.configs;

import com.scm.utils.JwtUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
public class AuthHandshakeInterceptor  implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request,
                                   @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler,
                                   @NonNull Map<String, Object> attributes) {

        System.out.println("[WS Auth] --- Bắt đầu Handshake ---");

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getParameter("token"); // Lấy JWT từ query param

            System.out.println("[WS Auth] Token parameter found: " + (token != null));
            System.out.println("[WS Auth] Raw token param value: " + token); // Log giá trị thô

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                System.out.println("[WS Auth] Extracted Bearer token: " + token);

                try {
                    // Sử dụng JwtUtils giả định (hoặc bạn dùng instance đã inject)
                    // Thay thế JwtUtils.validateTokenAndGetUsername(token) bằng cách gọi thực tế của bạn
                    String username = JwtUtils.validateTokenAndGetUsername(token);

                    System.out.println("[WS Auth] USER from TOKEN (after validation): " + username);

                    if (username != null) {
                        attributes.put("username", username);
                        System.out.println("[WS Auth] Username '" + username + "' added to attributes.");
                        System.out.println("[WS Auth] Handshake BEFORE returning true.");
                        return true; // Xác thực thành công
                    } else {
                        System.out.println("[WS Auth] Username is null after token validation.");
                    }
                } catch (Exception e) {
                    System.err.println("[WS Auth] Exception during token validation:");
                    e.printStackTrace(); // In stack trace đầy đủ
                }
            } else {
                System.out.println("[WS Auth] Token is null or does not start with 'Bearer '.");
            }
        } else {
            System.out.println("[WS Auth] Request is not ServletServerHttpRequest.");
        }

        System.out.println("[WS Auth] Handshake BEFORE returning false.");
        return false; // Xác thực thất bại

    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request,
                               @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler,
                               @Nullable Exception exception) {
        // No-op
    }
}
