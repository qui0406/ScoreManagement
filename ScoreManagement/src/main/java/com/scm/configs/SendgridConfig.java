package com.scm.configs;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class SendgridConfig {
    @Bean
    public SendGrid sendGrid(@Value("${spring.send_grid.api_key}") String apiKey) {
        return new SendGrid(apiKey);
    }
}
