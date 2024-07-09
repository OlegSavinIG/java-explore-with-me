package ru.practicum.explorewithme.event.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.model.mapper.CategoryMapper;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventStatus;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.model.UserRequest;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static EventResponse toResponse(EventEntity entity) {
        return EventResponse.builder()
                .id(entity.getId())
                .annotation(entity.getAnnotation())
                .description(entity.getDescription())
                .category(CategoryMapper.toResponse(entity.getCategory()))
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

    public static EventEntity toEntity(
            EventRequest request, CategoryEntity category, UserEntity user) {
        return EventEntity.builder()
                .annotation(request.getAnnotation())
                .category(category)
                .paid(request.getPaid())
                .state(request.getStateAction())
                .title(request.getTitle())
                .eventDate(request.getEventDate())
                .initiator(user)
                .description(request.getDescription())
                .participantLimit(request.getParticipantLimit())
                .requestModeration(request.getRequestModeration())
                .build();
    }
}
