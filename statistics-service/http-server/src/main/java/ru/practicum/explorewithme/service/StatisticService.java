package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    StatisticResponse saveStatistic(StatisticRequest request);

    List<StatisticResponse> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
