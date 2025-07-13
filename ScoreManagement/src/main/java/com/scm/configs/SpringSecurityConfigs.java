/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.scm.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scm.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author QUI
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = {
        "com.scm.controllers",
        "com.scm.filters",
        "com.scm.mapper",
        "com.scm.services",
        "com.scm.repositories"
})
@EnableTransactionManagement
public class SpringSecurityConfigs {
    @Autowired
    private JwtFilter jwtFilter;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**", "/home"
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/admin/**"
    };

    private static final String[] TEACHER_ENDPOINTS = {
            "/teacher/**"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
    


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http.csrf(c -> c.disable()).authorizeHttpRequests(requests
                        -> requests.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(TEACHER_ENDPOINTS).hasAnyRole("STAFF", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/users", true)
                        .failureUrl("/admin/login?error=true").permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/admin/login").permitAll());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    


//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return (request, response, authentication) -> {
//            boolean isAdmin = authentication.getAuthorities().stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//            if (isAdmin) {
//                response.sendRedirect("/admin/user"); // hoặc "/users"
//            } else {
//                request.getSession().invalidate(); // hủy session
//                response.sendRedirect("/login?error=access_denied"); // redirect lại login
//            }
//        };
//    }


}
