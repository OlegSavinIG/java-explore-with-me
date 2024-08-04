package ru.practicum.explorewithme.event.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.StatisticResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Client component for interacting with the event statistics service.
 */
@Component
@RequiredArgsConstructor
public class EventClient {
    /**
     * REST template for managing compilations.
     */
    private final RestTemplate restTemplate;
    /**
     * server.
     */
    @Value("${stats.server.url}")
    private String statsServerUrl;

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
