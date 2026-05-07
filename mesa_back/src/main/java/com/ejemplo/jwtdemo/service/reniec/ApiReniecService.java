package com.ejemplo.jwtdemo.service.reniec;

import com.ejemplo.jwtdemo.dto.reniec.Persona;
import com.ejemplo.jwtdemo.exception.ExternalApiException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@ComponentScan
@Service
public class ApiReniecService {
    private final WebClient webClient;

    public ApiReniecService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Persona> obtenerDatos(String dni) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/reniec/dni")
                        .queryParam("numero", dni)
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ExternalApiException(response.statusCode().value(), body)))
                )
                .bodyToMono(Persona.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(WebClientResponseException.class,
                        ex -> new ExternalApiException(ex.getRawStatusCode(), ex.getResponseBodyAsString()));
    }
}
