package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;
import ru.practicum.explorewithme.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for handling statistic requests.
 */
@RestController
@RequiredArgsConstructor
public class StatisticController {
    /**
     * Service for handling statistic requests.
     */
    private final StatisticService service;

    /**
     * Saves a statistic.
     *
     * @param request the statistic request
     * @return the saved statistic response
     */
    @PostMapping("/hit")
    public ResponseEntity<StatisticResponse> saveStatistic(
            @RequestBody final StatisticRequest request) {
        return ResponseEntity.ok(service.saveStatistic(request));
    }

    /**
     * Retrieves statistics.
     *
     * @param start the start date and time
     * @param end the end date and time
     * @param uris the list of URIs
     * @param unique whether to count only unique hits
     * @return a ResponseEntity containing the list of statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<List<StatisticResponse>> getStatistic(
            @RequestParam("start")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            final LocalDateTime end,
            @RequestParam(value = "uris", required = false)
            final List<String> uris,
            @RequestParam(value = "unique", defaultValue = "false")
            final boolean unique) {
        return ResponseEntity.ok(
                service.getStatistic(start, end, uris, unique));
    }
}
