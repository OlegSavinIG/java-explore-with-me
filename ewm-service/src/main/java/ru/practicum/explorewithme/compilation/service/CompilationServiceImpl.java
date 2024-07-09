package ru.practicum.explorewithme.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.model.CompilationEntity;
import ru.practicum.explorewithme.compilation.model.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.CompilationResponse;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.exception.NotExistException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final CompilationMapper mapper;

    @Override
    public List<CompilationResponse> getCompilations(
            Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<CompilationEntity> entities = repository.findAllByPinned(pinned, pageable);
        return entities.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponse getCompilation(Integer compId) {
        CompilationEntity entity = repository.findById(compId)
                .orElseThrow(() -> new NotExistException("Compilation doesnt exist"));
        return mapper.toResponse(entity);
    }
}
