package com.scm.configs;

import com.scm.pojo.User;
import com.scm.services.UserService;
import com.scm.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userDetailsService;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String username = authentication.getName();

        User user = userDetailsService.getUserByUsername(username);

        if (!user.getRole().equals("ROLE_STAFF")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ STAFF được đăng nhập qua form");
            return;
        }

        String token = JwtUtils.generateToken(user.getUsername(), user.getRole());
        session.setAttribute("authToken", token);

        Cookie cookie = new Cookie("AUTH_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
        response.sendRedirect("/ScoreManagement/admin/dashboard");
    }
}
