package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;
import ru.practicum.explorewithme.client.StatisticClient;
import ru.practicum.explorewithme.exception.WrongTimeException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for handling statistic requests.
 */
@RestController
@RequiredArgsConstructor
@Validated
public class StatisticController {
    /**
     * Client for handling statistic requests.
     */
    private final StatisticClient client;

    /**
     * Saves a statistic.
     *
     * @param request the statistic request
     * @return the saved statistic response
     */
    @PostMapping("/hit")
    public Mono<StatisticResponse> saveStatistic(
            @Valid @RequestBody final StatisticRequest request) {
        return client.saveStatistic(request);
    }

    /**
     * Retrieves statistics.
     *
     * @param start  the start date and time
     * @param end    the end date and time
     * @param uris   the list of URIs
     * @param unique whether to count only unique hits
     * @return a Flux containing the statistics
     */
    @GetMapping("/stats")
    public Flux<StatisticResponse> getStatistic(
            @NotNull @RequestParam("start")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime start,
            @NotNull @RequestParam("end")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime end,
            @RequestParam(value = "uris", required = false)
            final List<String> uris,
            @RequestParam(value = "unique", defaultValue = "false")
            final boolean unique) {
        if (!start.isBefore(end)) {
            throw new WrongTimeException(
                    "Начало эвента должно быть раньше конца");
        }
        return client.getStatistic(start, end, uris, unique);
    }
}
