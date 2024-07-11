package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.model.CategoryRequest;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.category.model.mapper.CategoryMapper;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.exists.ExistChecker;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository repository;
    private final ExistChecker checker;

    @Override
    public void deleteCategory(Integer catId) {
        checker.isCategoryExists(catId);
        repository.deleteById(catId);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest category) {
        CategoryEntity entity = repository.save(CategoryMapper.toEntity(category));
        return CategoryMapper.toResponse(entity);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest category, Integer catId) {
        checker.isCategoryExists(catId);
        CategoryEntity categoryEntity = repository.findById(catId).get();
        if (category.getName() != null) {
            categoryEntity.setName(category.getName());
        }
        CategoryEntity saved = repository.save(categoryEntity);
        return CategoryMapper.toResponse(saved);
    }
}
