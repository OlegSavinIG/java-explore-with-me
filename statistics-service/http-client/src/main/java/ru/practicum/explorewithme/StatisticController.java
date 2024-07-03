package ru.practicum.explorewithme;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.exception.WrongTimeException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatisticController {
    private final StatisticClient client;

    @PostMapping("/hit")
    public Mono<StatisticResponse> saveStatistic(@Valid @RequestBody StatisticRequest request) {
        return client.saveStatistic(request);
    }

    @GetMapping("/stats")
    public Flux<StatisticResponse> getStatistic(@NotNull @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                @NotNull @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                @RequestParam(value = "uris", required = false) List<String> uris,
                                                @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        if (!start.isBefore(end)) {
            throw new WrongTimeException("Начало эвента должно быть раньше конца");
        }
        return client.getStatistic(start, end, uris, unique);
    }
}
