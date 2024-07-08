package ru.practicum.explorewithme.user.service.privateuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.repository.RequestRepository;
import ru.practicum.explorewithme.user.request.model.*;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PrivateUserRequestServiceImpl implements PrivateUserRequestService {
    private final RequestRepository repository;
    private final EventService eventService;
    @Override
    public List<UserEventRequestDto> getEventRequests(Long userId, Long eventId) {
        EventResponse event = eventService.getEvent(eventId);
        if (event.getInitiator().getId() == userId) {
            List<UserEventRequestEntity> eventRequestEntitys = repository.findAllByEventId(eventId)
                    .orElseThrow(() -> new NotExistException("Even does not have requests"));

        }

    }

    @Override
    public EventRequestStatusUpdateResult approveRequests(Long userId, Long eventId, ApproveRequestCriteria criteria) {
        return null;
    }

    @Override
    public List<UserEventRequestDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public UserEventRequestDto createRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public UserEventRequestDto cancelRequest(Long userId, Integer requestId) {
        return null;
    }
}
