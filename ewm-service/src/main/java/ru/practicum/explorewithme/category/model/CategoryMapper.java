package ru.practicum.explorewithme.category.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {
    public static CategoryResponse toResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder().build();
    }
}
