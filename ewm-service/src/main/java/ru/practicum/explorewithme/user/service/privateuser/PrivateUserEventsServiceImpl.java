package ru.practicum.explorewithme.user.service.privateuser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.category.model.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.service.CategoryService;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.model.UserResponse;
import ru.practicum.explorewithme.user.service.admin.AdminUserService;
import ru.practicum.explorewithme.user.service.admin.AdminUserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateUserEventsServiceImpl implements PrivateUserEventsService {
    private final EventRepository repository;
    private final AdminUserService adminUserService;
    private final CategoryService categoryService;
    @Override
    public List<EventResponse> getEventsByUserId(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from/size, size);
       Page<EventEntity> eventEntities = repository.findAllByUserId(userId, pageable)
               .orElseThrow(() -> new NotExistException("This user does not have events"));
        return eventEntities.stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getByUserIdAndEventId(Long userId, Long eventId) {
       EventEntity entity = repository.findByEventIdAndUserId(eventId, userId)
               .orElseThrow(() -> new NotExistException("This event does not exist"));
        return EventMapper.toResponse(entity);
    }

    @Override
    public EventResponse createEvent(EventRequest request, Long userId) {
        UserResponse userResponse = adminUserService.findById(userId);
        CategoryResponse category = categoryService.getCategory(request.getCategory());
        repository.save(EventMapper.toEntity(request, CategoryMapper.toEntity(category)));
        return null;
    }

    @Override
    public EventResponse updateEvent(Long userId, Long eventId, EventRequest request) {
        EventEntity entity = repository.findByEventIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotExistException("This event does not exist"));
        return null;
    }
}
