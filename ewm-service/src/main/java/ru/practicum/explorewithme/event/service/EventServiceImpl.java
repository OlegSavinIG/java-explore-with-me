package ru.practicum.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventSearchCriteria;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotExistException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    @Override
    public List<EventResponse> getEvents(EventSearchCriteria criteria, Integer from, Integer size) {

        return null;
    }

    @Override
    public EventResponse getEvent(Long id) {
        EventEntity eventEntity = repository.findById(id)
                .orElseThrow(() -> new NotExistException("This event does not exist"));
        return EventMapper.toResponse(eventEntity);
    }
}
