package ru.practicum.explorewithme.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.StatisticEntity;
import ru.practicum.explorewithme.StatisticRequest;
import ru.practicum.explorewithme.StatisticResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticMapper {
    public static StatisticEntity toEntity(StatisticRequest request) {
        return StatisticEntity.builder()
                .app(request.getApp())
                .uri(request.getUri())
                .ip(request.getIp())
                .creationTime(request.getCreationTime())
                .build();
    }

    public static StatisticResponse toResponse(StatisticEntity entity) {
        return StatisticResponse.builder()
                .app(entity.getApp())
                .uri(entity.getUri())
                .ip(entity.getIp())
                .creationTime(entity.getCreationTime())
                .id(entity.getId())
                .build();
    }
}
