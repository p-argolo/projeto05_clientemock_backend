package com.simulador.backend.client;

import com.simulador.backend.dto.LocationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SafetyPlaceClient {
    private final WebClient webClient;

    // Inicializa o webClient com a URL base
    public SafetyPlaceClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://projeto05-backend.onrender.com").build();
    }

    // Envia um objeto LocationDTO para o endpoint do SDK
    public String sendToSdk(String token, LocationDTO locationDTO) {
        return webClient.post()
                .uri("/token/metadata")
                .header("Authorization", "Bearer " + token)
                .bodyValue(locationDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
