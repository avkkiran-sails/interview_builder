package com.sails.software.interview.builder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Configuration
public class AppConfig {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean
    public RestTemplate getRestTemplate() {
        logger.info("Creating rest template for application");
        return new RestTemplate();
    }
}
