package ru.practicum.explorewithme.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.StatisticEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticRepository extends ReactiveCrudRepository<StatisticEntity, Long> {
    Flux<StatisticEntity> findAllStatistic(LocalDateTime start, LocalDateTime end);
    Flux<StatisticEntity> findAllStatisticWithUniqueIp(LocalDateTime start, LocalDateTime end);
    Flux<StatisticEntity> findAllStatisticByUris(LocalDateTime start, LocalDateTime end, List<String> uris);
    Flux<StatisticEntity> findAllStatisticByUrisWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
