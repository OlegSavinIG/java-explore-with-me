package ru.practicum.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventResponseShort;
import ru.practicum.explorewithme.event.model.EventSearchCriteria;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.specification.EventSpecification;
import ru.practicum.explorewithme.exception.NotExistException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;

    @Override
    public List<EventResponseShort> getEvents(EventSearchCriteria criteria, Integer from, Integer size) {
        Specification<EventEntity> spec = Specification.where(null);

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            spec = spec.and(EventSpecification.hasCategories(criteria.getCategories()));
        }
        spec = spec.and(EventSpecification.dateAfter(criteria.getRangeStart()));

        spec = spec.and(EventSpecification.dateBefore(criteria.getRangeEnd()));
        if (criteria.getText() != null && !criteria.getText().isEmpty()) {
            spec = spec.and(EventSpecification.containsText(criteria.getText()));
        }
        if (Boolean.TRUE.equals(criteria.getOnlyAvailable())) {
            spec = spec.and(EventSpecification.isAvailable());
        }
        if (criteria.getPaid() != null) {
            spec = spec.and(EventSpecification.isPaid(criteria.getPaid()));
        }
        Sort sort = Sort.unsorted();
        if ("EVENT_DATE".equalsIgnoreCase(criteria.getSort())) {
            sort = Sort.by(Sort.Direction.ASC, "eventDate");
        }
        if ("VIEWS".equalsIgnoreCase(criteria.getSort())) {
            sort = Sort.by(Sort.Direction.DESC, "views");
        }
        Pageable pageable = PageRequest.of(from / size, size, sort);
        Page<EventEntity> eventEntities = repository.findAll(spec, pageable);
        return eventEntities.stream()
                .map(EventMapper::toResponseShort)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getEvent(Long id) {
        EventEntity eventEntity = repository.findById(id)
                .orElseThrow(() -> new NotExistException("This event does not exist"));
        return EventMapper.toResponse(eventEntity);
    }

    @Override
    public List<EventResponse> getEventsByIds(List<Long> ids) {
        List<EventEntity> eventEntities = repository.findAllById(ids);
        if (eventEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return eventEntities.stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventEntity getEventEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotExistException("Event does not exist"));
    }

    public List<EventEntity> getEventEntities(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
