package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.specification.EventSpecification;
import ru.practicum.explorewithme.exception.InvalidEventStateException;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.model.EventSearchCriteriaForAdmin;
import ru.practicum.explorewithme.user.repository.AdminEventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final AdminEventRepository repository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventResponse> getEvents(
            EventSearchCriteriaForAdmin criteria, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Specification<EventEntity> spec = Specification.where(null);

        if (criteria.getUsers() != null && !criteria.getUsers().isEmpty()) {
            spec = spec.and(EventSpecification.hasUsers(criteria.getUsers()));
        }

        if (criteria.getStates() != null && !criteria.getStates().isEmpty()) {
            spec = spec.and(EventSpecification.hasStates(criteria.getStates()));
        }

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            spec = spec.and(EventSpecification.hasCategories(criteria.getCategories()));
        }

        if (criteria.getRangeStart() != null) {
            spec = spec.and(EventSpecification.dateAfter(criteria.getRangeStart()));
        }

        if (criteria.getRangeEnd() != null) {
            spec = spec.and(EventSpecification.dateBefore(criteria.getRangeEnd()));
        }
        Page<EventEntity> eventEntities = repository.findAll(spec, pageable);
        return eventEntities.stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse approveEvent(EventRequest request, Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotExistException(
                        "Event with id=" + eventId + " was not found"));
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            CategoryEntity category = categoryRepository.findById(
                            request.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            event.setCategory(category);
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getStateAction() != null) {
            handleStateAction(request.getStateAction(), event);
        }

        eventRepository.save(event);
        return EventMapper.toResponse(event);
    }

    private void handleStateAction(String stateAction, EventEntity event) {
        switch (stateAction) {
            case "PUBLISH_EVENT":
                if (!"PENDING".equals(event.getState())) {
                    throw new InvalidEventStateException(
                            "Cannot publish the event because it's not in the right state: " + event.getState());
                }
                event.setState("PUBLISHED");
                event.setPublishedOn(LocalDateTime.now());
                break;
            case "REJECT_EVENT":
                if ("PUBLISHED".equals(event.getState())) {
                    throw new InvalidEventStateException(
                            "Cannot reject the event because it's already published");
                }
                event.setState("REJECTED");
                break;
            default:
                throw new IllegalArgumentException("Invalid state action: " + stateAction);
        }
    }
}
