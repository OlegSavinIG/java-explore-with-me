package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.StatisticEntity;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;
import ru.practicum.explorewithme.client.StatisticClient;
import ru.practicum.explorewithme.mapper.StatisticMapper;
import ru.practicum.explorewithme.repository.StatisticRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the StatisticService interface.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository repository;
    private final StatisticClient client;

    @Override
    public void saveStatistic(final StatisticRequest request) {
        repository.save(StatisticMapper.toEntity(request));
    }

    @Override
    public List<StatisticResponse> getStatistic(final LocalDateTime start, final LocalDateTime end, final List<String> uris, final boolean unique) {
        List<StatisticEntity> statistics;
        if (unique) {
            if (uris != null && !uris.isEmpty()) {
                statistics = repository.findAllStatisticByUrisWithUniqueIp(start, end, uris);
            } else {
                statistics = repository.findAllStatisticWithUniqueIp(start, end);
            }
        } else {
            if (uris != null && !uris.isEmpty()) {
                statistics = repository.findAllStatisticByUris(start, end, uris);
            } else {
                statistics = repository.findAllStatistic(start, end);
            }
        }

        List<StatisticResponse> responses = statistics.stream()
                .map(StatisticMapper::toResponse)
                .collect(Collectors.toList());

        client.sendStatistics(responses);

        return responses;
    }
}
