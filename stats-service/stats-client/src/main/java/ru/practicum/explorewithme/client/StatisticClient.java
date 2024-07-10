package ru.practicum.explorewithme.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                .bodyToMono(Void.class)
                .doOnSuccess(response -> log.info("Successfully sent statistics to external service"))
                .doOnError(error -> log.error("Failed to send statistics to external service", error));
    }
}
