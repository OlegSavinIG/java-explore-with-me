package ru.practicum.explorewithme.event.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventStatus;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static EventResponse toResponse(EventEntity entity) {
        return EventResponse.builder()
                .id(entity.getId())
                .annotation(entity.getAnnotation())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .eventDate(entity.getEventDate())
                .confirmedRequests(entity.getConfirmedRequests())
                .createdOn(entity.getCreatedOn())
                .state(EventStatus.valueOf(entity.getState()))
                .title(entity.getTitle())
                .views(entity.getViews())
                .paid(entity.isPaid())
                .initiator(UserMapper.toResponseWithEvent(entity.getInitiator()))
                .participantLimit(entity.getParticipantLimit())
                .publishedOn(entity.getPublishedOn())
                .requestModeration(entity.isRequestModeration())
                .build();
    }
}
