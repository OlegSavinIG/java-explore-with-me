package ru.practicum.explorewithme.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.category.model.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.NotExistException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<CategoryResponse> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<CategoryEntity> categoryEntities = repository.findAll(pageable);
        return categoryEntities.stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategory(Integer catId) {
        CategoryEntity categoryEntity =
                repository.findById(catId)
                        .orElseThrow(() -> new NotExistException("This category does not exist"));
        return CategoryMapper.toResponse(categoryEntity);
    }

    @Override
    public CategoryEntity getCategoryEntity(Integer catId) {
        return repository.findById(catId)
                .orElseThrow(() -> new NotExistException("This category does not exist"));
    }
}
