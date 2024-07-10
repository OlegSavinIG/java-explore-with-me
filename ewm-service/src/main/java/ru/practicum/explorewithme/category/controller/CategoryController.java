package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService service;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received request to get categories from {} with size {}", from, size);
        List<CategoryResponse> categories = service.getCategories(from, size);
        log.info("Returning {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Integer catId) {
        log.info("Received request to get category with ID {}", catId);
        CategoryResponse category = service.getCategory(catId);
        log.info("Returning category: {}", category);
        return ResponseEntity.ok(category);
    }
}
