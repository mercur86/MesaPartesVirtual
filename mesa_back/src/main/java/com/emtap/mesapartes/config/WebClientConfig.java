package com.emtap.mesapartes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Value("${decolecta.token}")
    private String tokenDecolecta;
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.decolecta.com/") // URL base del API externo
                .defaultHeader("Authorization", "Bearer "+tokenDecolecta)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
