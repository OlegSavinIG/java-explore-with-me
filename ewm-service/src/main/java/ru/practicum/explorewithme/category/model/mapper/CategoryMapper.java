package ru.practicum.explorewithme.category.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.model.CategoryResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {
    public static CategoryResponse toResponse(CategoryEntity categoryEntity) {
        return CategoryResponse.builder().build();
    }

    public static CategoryEntity toEntity(CategoryResponse category) {
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
