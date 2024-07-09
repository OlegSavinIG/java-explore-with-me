package ru.practicum.explorewithme.user.service.privateuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.exception.AlreadyExistException;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.exists.ExistChecker;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.repository.RequestRepository;
import ru.practicum.explorewithme.user.request.model.*;
import ru.practicum.explorewithme.user.service.admin.AdminUserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateUserRequestServiceImpl implements PrivateUserRequestService {
    private final RequestRepository repository;
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final AdminUserService adminUserService;
    private final ExistChecker checker;

    @Override
    public List<UserEventRequestDto> getEventRequests(Long userId, Long eventId) {
        checker.isEventExists(eventId);
        checker.isUserExist(userId);
        EventResponse event = eventService.getEvent(eventId);
        if (event.getInitiator().getId() == userId) {
            List<UserEventRequestEntity> eventRequestEntities = repository.findAllByEventId(eventId)
                    .orElseThrow(() -> new NotExistException("Even does not have requests"));
            return eventRequestEntities.stream()
                    .map(UserEvenRequestMapper::toDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public EventRequestStatusUpdateResult approveRequests(
            Long userId, Long eventId, ApproveRequestCriteria criteria) {
        checker.isUserExist(userId);
        EventEntity event = eventService.getEventEntity(eventId);
        if (event.getRequestModeration().equals(Boolean.FALSE)) {
            throw new IllegalArgumentException("Event doesn't have moderation");
        }
        if (event.getInitiator().getId() != userId) {
            throw new IllegalArgumentException("Wrong userId or eventId");
        }
        List<Long> ids = criteria.getIds();
        String status = criteria.getStatus();
        ExecutorService executor = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<UserEventRequestEntity> requests = repository.findAllById(ids);
        try {
            List<Callable<Void>> tasks = requests.stream()
                    .map(request -> (Callable<Void>) () -> {
                                updateRequestStatus(request, status);
                                return null;
                            }
                    )
                    .collect(Collectors.toList());
            List<Future<Void>> futures = executor.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception e) {

        } finally {
            executor.shutdown();
        }
        if (status.equals("CONFIRMED")) {
            int confirmedRequests = event.getConfirmedRequests();
            event.setConfirmedRequests(confirmedRequests + requests.size());
            eventRepository.save(event);
        }
        return null;
    }

    @Override
    public List<UserEventRequestDto> getUserRequests(Long userId) {
        boolean existsById = repository.existsById(userId);
        if (!existsById) {
            throw new NotExistException("User not exist");
        }
        List<UserEventRequestEntity> eventRequestEntities =
                repository.findAllByUserId(userId);
        return eventRequestEntities.stream()
                .map(UserEvenRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserEventRequestDto createRequest(Long userId, Long eventId) {
        boolean existed = repository.existsByRequesterIdAndEventId(userId, eventId);
        if (existed) {
            throw new AlreadyExistException("User already have request" +
                    "for this event");
        }
        EventEntity entity = eventService.getEventEntity(eventId);
        UserEntity userEntity = adminUserService.findUserEntity(userId);
        UserEventRequestEntity eventRequestEntity = UserEventRequestEntity.builder()
                .status("PENDING")
                .created(LocalDateTime.now())
                .requester(userEntity)
                .event(entity)
                .build();
        repository.save(eventRequestEntity);
        return UserEvenRequestMapper.toDto(eventRequestEntity);
    }

    @Override
    public UserEventRequestDto cancelRequest(Long userId, Long requestId) {
        checker.isUserExist(userId);
        checker.isRequestExists(requestId);
        UserEventRequestEntity entity = repository.findByIdAndByUserId(requestId, userId)
                .orElseThrow(() -> new NotExistException("Request does not found"));
        entity.setStatus("CANCELED");
        repository.delete(entity);
        return UserEvenRequestMapper.toDto(entity);
    }

    private void updateRequestStatus(UserEventRequestEntity entity, String status) {
        entity.setStatus(status);
        repository.save(entity);
    }
}
