package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventSearchCriteria;

import java.util.List;

public interface EventService {
    List<EventResponse> getEvents(EventSearchCriteria criteria, Integer from, Integer size);

    EventResponse getEvent(Long id);
}
