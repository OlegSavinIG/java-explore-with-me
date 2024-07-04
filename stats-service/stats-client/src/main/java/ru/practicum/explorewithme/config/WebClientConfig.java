package ru.practicum.explorewithme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * Configuration for WebClient.
 */
@Configuration
public class WebClientConfig {

    /**
     * Configures the WebClient bean.
     *
     * @param builder the WebClient.Builder
     * @param serverUrl the base URL for the server
     * @return the configured WebClient
     */
    @Bean
    public WebClient webClient(final WebClient.Builder builder,
                               @Value("${server.url}")
                               final String serverUrl) {
        return builder
                .baseUrl(serverUrl)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(
                        new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(5))))
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    /**
     * Logs the details of the WebClient request.
     *
     * @return the ExchangeFilterFunction for logging requests
     */
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
            System.out.println(
                    "Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach(
                    (name, values) -> values.forEach(
                            value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    /**
     * Logs the details of the WebClient response.
     *
     * @return the ExchangeFilterFunction for logging responses
     */
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
            System.out.println(
                    "Response Status: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach(
                    (name, values) -> values.forEach(
                            value -> System.out.println(name + ": " + value)));
            return Mono.just(clientResponse);
        });
    }
}
