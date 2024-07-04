package ru.practicum.explorewithme.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service for handling statistics via web client.
 */
@Service
@RequiredArgsConstructor
public class StatisticClient {

    private final WebClient webClient;

    /**
     * Saves a statistic.
     *
     * @param request the request containing the statistic data
     * @return the response containing the saved statistic data
     */
    public Mono<StatisticResponse> saveStatistic
    (final StatisticRequest request) {
        return webClient.post()
                .uri("/hit")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StatisticResponse.class);
    }

    /**
     * Retrieves statistics.
     *
     * @param start the start date and time
     * @param end the end date and time
     * @param uris the list of URIs
     * @param unique whether to count only unique hits
     * @return a Flux containing the statistics
     */
    public Flux<StatisticResponse> getStatistic
    (final LocalDateTime start, final LocalDateTime end,
                                                final List<String> uris, final boolean unique) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime =
                URLEncoder.encode(start.format(formatter), StandardCharsets.UTF_8);
        String endTime =
                URLEncoder.encode(end.format(formatter), StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("/stats")
                .queryParam("start", startTime)
                .queryParam("end", endTime)
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                builder.queryParam("uris", uri);
            }
        }
        return webClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .bodyToFlux(StatisticResponse.class);
    }
}
