package ru.practicum.explorewithme.user.service.admin;

import ru.practicum.explorewithme.category.model.CategoryRequest;
import ru.practicum.explorewithme.category.model.CategoryResponse;

public interface AdminCategoryService {
    void deleteCategory(Long catId);

    CategoryResponse createCategory(CategoryRequest category);

    CategoryResponse updateCategory(CategoryRequest category, Long catId);
}
