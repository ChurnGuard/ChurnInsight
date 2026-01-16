package com.team42.churninsight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${ml-service.url}") String mlServiceUrl) {
        return WebClient.builder()
                .baseUrl(mlServiceUrl)
                .build();
    }
}
