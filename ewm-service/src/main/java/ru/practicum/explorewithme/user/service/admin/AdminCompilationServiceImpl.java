package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.model.CompilationEntity;
import ru.practicum.explorewithme.compilation.model.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.CompilationRequest;
import ru.practicum.explorewithme.compilation.model.CompilationResponse;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.mapper.EventMapper;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.repository.AdminCompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final AdminCompilationRepository repository;
    private final CompilationMapper mapper;
    private final EventService eventService;

    @Override
    public CompilationResponse createCompilation(CompilationRequest request) {
        CompilationEntity entity = repository.save(mapper.toEntity(request, eventService));
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void deleteCompilationById(Integer compId) {
        boolean existsById = repository.existsById(compId);
        if (!existsById) {
            throw new NotExistException("This compilation not exist");
        }
        repository.deleteById(compId);
    }

    @Override
    public CompilationResponse updateCompilation(CompilationRequest request, Integer compId) {
        CompilationEntity entity = repository.findById(compId)
                .orElseThrow(() -> new NotExistException(
                        "This compilation does not exist"));
        if (request.getPinned() != null) {
            entity.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getEvents() != null && !request.getEvents().isEmpty()) {
            List<EventEntity> eventsByIds =
                    eventService.getEventEntities(request.getEvents());
            entity.setEvents(eventsByIds);
        }
        repository.save(entity);
        return mapper.toResponse(entity);
    }
}
