package ru.practicum.explorewithme;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticClient client;

    @PostMapping("/hit")
    public Mono<StatisticResponse> saveStatistic(@RequestBody StatisticRequest request) {
       return client.saveStatistic(request);
    }

    @GetMapping("/stats")
    public Flux<StatisticResponse> getStatistic(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                @RequestParam(value = "uris", required = false) List<String> uris,
                                                @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        return client.getStatistic(start, end, uris, unique);
    }
}
