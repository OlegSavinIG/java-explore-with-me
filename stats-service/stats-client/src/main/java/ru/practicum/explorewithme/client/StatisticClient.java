package ru.practicum.explorewithme.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.StatisticResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Service for handling statistics via RestTemplate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticClient {
    /**
     * Template for handling statistics via RestTemplate.
     */
    private final RestTemplate restTemplate;
    /**
     * server.
     */
    @Value("${stats.server.url}")
    private String statsServerUrl;


    /**
     * Sends a list of statistics to an external service.
     *
     * @param statistics the list of statistics to send
     */
    public void sendStatistics(final List<StatisticResponse> statistics) {
        try {
            restTemplate.postForObject(
                    "/external-service-endpoint",
                    statistics,
                    Void.class
            );
            log.info("Successfully sent statistics to external service");
        } catch (Exception e) {
            log.error("Failed to send statistics to external service", e);
        }
    }

    /**
     * Sends request data to the statistics server.
     *
     * @param remoteAddr the remote address of the request
     * @param requestURI the URI of the request
     */
    public void sendRequestData(
            final String remoteAddr, final String requestURI) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("remoteAddr", remoteAddr);
        requestData.put("requestURI", requestURI);
        restTemplate.postForObject(
                statsServerUrl + "/hit", requestData, Void.class);
    }

    /**
     * Asynchronously retrieves the number of views for an event.
     *
     * @param eventId the ID of the event
     * @return a CompletableFuture containing the number of event views
     */
    @Async
    public CompletableFuture<Integer> getEventViews(
            final Long eventId) {
        String url = String.format(
                "%s/stats?start=%s&end=%s&uris=/events/%d",
                statsServerUrl,
                LocalDateTime.now().minusYears(1)
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.now()
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss")),
                eventId);
        List<StatisticResponse> response = List.of(
                restTemplate.getForObject(url, StatisticResponse[].class));
        return CompletableFuture.completedFuture(response.size());
    }
}
