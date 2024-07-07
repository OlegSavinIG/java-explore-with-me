package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.user.model.EventSearchCriteria;
import ru.practicum.explorewithme.user.repository.AdminEventRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final AdminEventRepository repository;
    @Override
    public List<EventResponse> getEvents(EventSearchCriteria criteria, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventResponse approveEvent(EventRequest request, Long eventId) {
        return null;
    }
}
