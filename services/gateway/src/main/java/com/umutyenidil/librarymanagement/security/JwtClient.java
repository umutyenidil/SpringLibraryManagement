package com.umutyenidil.librarymanagement.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Map> validateToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/api/v1/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Map.class)
                .onErrorResume(WebClientResponseException.Unauthorized.class, ex -> Mono.error(new RuntimeException("Invalid token")))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Error contacting user-service")));
    }
}