package com.ejemplo.jwtdemo.service.sunat;

import com.ejemplo.jwtdemo.dto.sunat.EmpresaSunat;
import com.ejemplo.jwtdemo.exception.ExternalApiException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@ComponentScan
@Service
public class ApiSunatService {

    private final WebClient webClient;

    public ApiSunatService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<EmpresaSunat> obtenerDatos(String ruc) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/sunat/ruc")
                        .queryParam("numero", ruc)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono
                                        .error(new ExternalApiException(response.statusCode().value(), body))))
                .bodyToMono(EmpresaSunat.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(WebClientResponseException.class,
                        ex -> new ExternalApiException(ex.getStatusCode().value(), ex.getResponseBodyAsString()));
    }
}
