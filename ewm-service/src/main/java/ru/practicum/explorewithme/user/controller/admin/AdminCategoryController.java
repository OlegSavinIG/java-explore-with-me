package ru.practicum.explorewithme.user.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.user.service.admin.AdminCategoryService;

import javax.validation.Valid;

@RestController("/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService service;


    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest category
    ) {
        return ResponseEntity.ok(service.createCategory(category));
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        service.deleteCategory(catId);
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long catId,
            @Valid @RequestBody CategoryRequest category) {
        return service.updateCategory(category, catId);
    }
}
