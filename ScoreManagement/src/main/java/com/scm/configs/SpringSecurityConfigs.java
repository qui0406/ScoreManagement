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
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
        "com.scm.repositories",
        "com.scm.validators",
        "com.scm.configs"
})
@EnableTransactionManagement
public class SpringSecurityConfigs {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**", "/email/**"
    };

    private static final String[] STAFF_ENDPOINTS = {
            "/api/secure/staff/**",
    };

    private static final String[] TEACHER_ENDPOINTS = {
        "/api/secure/teacher/**", "/api/secure/common/**"
    };

    private static final String[] TEACHER_SUPER_ENDPOINTS = {
        "/api/secure/teacher-super/**", "/api/secure/common/**"
    };

    private static final String[] USER_ENDPOINTS = {
        "/api/secure/user/**", "/api/secure/common/**"
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
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable()).authorizeHttpRequests(requests
                -> requests
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/login", "/logout").permitAll()
                        .requestMatchers(STAFF_ENDPOINTS).hasAnyRole("STAFF")
                        .requestMatchers(TEACHER_SUPER_ENDPOINTS).hasAnyRole("TEACHER_SUPER")
                        .requestMatchers(TEACHER_ENDPOINTS).hasAnyRole("TEACHER")
                        .requestMatchers(USER_ENDPOINTS).hasAnyRole("USER", "TEACHER")
                        .anyRequest().authenticated())

                .formLogin(form -> form.loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .successHandler(successHandler)
                        .failureUrl("/admin/login?error=true").permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/admin/login").permitAll());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
