package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;
import ru.practicum.explorewithme.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService service;

    @PostMapping("/hit")
    public ResponseEntity<StatisticResponse> saveStatistic(@RequestBody StatisticRequest request) {
        return ResponseEntity.ok(service.saveStatistic(request));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatisticResponse>> getStatistic(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                                @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                                @RequestParam(value = "uris", required = false) List<String> uris,
                                                                @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        return ResponseEntity.ok(service.getStatistic(start, end, uris, unique));
    }
}
