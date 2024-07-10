package ru.practicum.explorewithme.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.StatisticResponse;

import java.util.List;

/**
 * Service for handling statistics via web client.
 */
@Service
@RequiredArgsConstructor
public class StatisticClient {
    /**
     * Client for handling statistics via web client.
     */
    private final WebClient webClient;

    public Mono<Void> sendStatistics(List<StatisticResponse> statistics) {
        return webClient.post()
                .uri("/external-service-endpoint")
                .bodyValue(statistics)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
