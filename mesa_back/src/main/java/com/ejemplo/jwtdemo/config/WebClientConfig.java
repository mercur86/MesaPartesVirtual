package com.ejemplo.jwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.decolecta.com/") // URL base del API externo
                .defaultHeader("Authorization", "Bearer sk_7825.jyG4d88J0cttJU8q2gxstx1y2kqgc29E")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
