package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.StatisticEntity;
import ru.practicum.explorewithme.repository.StatisticRepository;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;
import ru.practicum.explorewithme.mapper.StatisticMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;

    @Override
    public StatisticResponse saveStatistic(StatisticRequest request) {
        return StatisticMapper.toResponse(repository.save(StatisticMapper.toEntity(request)));
    }

    @Override
    public List<StatisticResponse> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<StatisticEntity> statistic;
        if (unique) {
            statistic = repository.findAllStatisticWithUniqueIp(start, end);
            if (!uris.isEmpty()) {
                statistic = repository.findAllStatisticByUrisWithUniqueIp(start, end, uris);
            }
        } else {
            statistic = repository.findAllStatistic(start, end);
            if (!uris.isEmpty()) {
                statistic = repository.findAllStatisticByUris(start, end, uris);
            }
        }
        return statistic.stream()
                .map(StatisticMapper::toResponse)
                .collect(Collectors.toList());
    }
}
