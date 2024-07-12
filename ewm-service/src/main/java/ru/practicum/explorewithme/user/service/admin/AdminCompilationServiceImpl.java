package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.model.CompilationEntity;
import ru.practicum.explorewithme.compilation.model.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.CompilationRequest;
import ru.practicum.explorewithme.compilation.model.CompilationResponse;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.repository.AdminCompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final AdminCompilationRepository repository;
    private final CompilationMapper mapper;
    private final EventService eventService;

    @Override
    @Transactional
    public CompilationResponse createCompilation(CompilationRequest request) {
        log.info("Creating compilation with title: {}", request.getTitle());
        CompilationEntity entity = repository.save(mapper.toEntity(request, eventService));
        CompilationResponse response = mapper.toResponse(entity);
        log.info("Created compilation with id: {}", response.getId());
        return response;
    }

    @Override
    @Transactional
    public void deleteCompilationById(Integer compId) {
        log.info("Deleting compilation with id: {}", compId);
        boolean existsById = repository.existsById(compId);
        if (!existsById) {
            log.warn("Compilation with id {} does not exist", compId);
            throw new NotExistException("This compilation not exist");
        }
        repository.deleteById(compId);
        log.info("Deleted compilation with id: {}", compId);
    }

    @Override
    @Transactional
    public CompilationResponse updateCompilation(CompilationRequest request, Integer compId) {
        log.info("Updating compilation with id: {}", compId);
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
        CompilationResponse response = mapper.toResponse(entity);
        log.info("Updated compilation with id: {}", response.getId());
        return response;
    }
}
