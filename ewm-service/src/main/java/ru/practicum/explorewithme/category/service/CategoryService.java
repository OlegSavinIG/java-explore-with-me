package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.model.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories(Integer from, Integer size);

    CategoryResponse getCategory(Integer catId);
}
