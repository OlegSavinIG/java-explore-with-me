package ru.practicum.explorewithme.user.service.privateuser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.category.model.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.service.CategoryService;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.exists.ExistChecker;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.service.admin.AdminUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateUserEventsServiceImpl implements PrivateUserEventsService {
    private final EventRepository repository;
    private final AdminUserService adminUserService;
    private final CategoryService categoryService;
    private final ExistChecker checker;

    @Override
    public List<EventResponse> getEventsByUserId(Long userId, Integer from, Integer size) {
        checker.isUserExist(userId);
        Pageable pageable = PageRequest.of(from / size, size);
        Page<EventEntity> eventEntities = repository.findAllByUserId(userId, pageable)
                .orElseThrow(() -> new NotExistException("This user does not have events"));
        return eventEntities.stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getByUserIdAndEventId(Long userId, Long eventId) {
        checker.isUserExist(userId);
        checker.isEventExists(eventId);
        EventEntity entity = repository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotExistException("This event does not exist"));
        return EventMapper.toResponse(entity);
    }

    @Override
    public EventResponse createEvent(EventRequest request, Long userId) {
        checker.isUserExist(userId);
        UserEntity userEntity = adminUserService.findUserEntity(userId);
        CategoryResponse category = categoryService.getCategory(request.getCategory());
        EventEntity eventEntity = repository
                .save(EventMapper.toEntity(
                        request, CategoryMapper.toEntity(category), userEntity));
        eventEntity.setCreatedOn(LocalDateTime.now());
        return EventMapper.toResponse(eventEntity);
    }

    @Transactional
    @Override
    public EventResponse updateEvent(Long userId, Long eventId, EventRequest request) {
        checker.isUserExist(userId);
        checker.isEventExists(eventId);
        EventEntity entity = repository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotExistException("This event does not exist"));

        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getAnnotation() != null) {
            entity.setAnnotation(request.getAnnotation());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            entity.setEventDate(request.getEventDate());
        }
        if (request.getPaid() != null) {
            entity.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            entity.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            entity.setRequestModeration(request.getRequestModeration());
        }
        if (request.getStateAction() != null) {
            entity.setState(request.getStateAction());
        }
        if (request.getCategory() != null) {
            CategoryEntity category = categoryService.getCategoryEntity(request.getCategory());
            entity.setCategory(category);
        }

        repository.save(entity);

        return EventMapper.toResponse(entity);
    }
}
