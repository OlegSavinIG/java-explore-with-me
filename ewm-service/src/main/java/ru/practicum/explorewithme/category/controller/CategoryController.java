package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Integer catId) {
        return ResponseEntity.ok(service.getCategory(catId));
    }
}
