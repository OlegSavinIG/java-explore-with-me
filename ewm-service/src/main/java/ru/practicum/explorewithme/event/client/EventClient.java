package ru.practicum.explorewithme.event.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventClient {
    private final RestTemplate restTemplate;

    @Value("${stats.server.url}")
    private String statsServerUrl;

    public void sendRequestData(String remoteAddr, String requestURI) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("remoteAddr", remoteAddr);
        requestData.put("requestURI", requestURI);
        restTemplate.postForObject(statsServerUrl + "/receive-data", requestData, Void.class);
    }
}
