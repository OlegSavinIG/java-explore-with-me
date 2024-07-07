package ru.practicum.explorewithme.user.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.user.service.admin.AdminCompilationService;

import javax.validation.Valid;

@RestController("/admin")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService service;

    @PostMapping("/compilations")
    public ResponseEntity<Compilation> createCompilation(
            @Valid @RequestBody Compilation compilation) {
        return ResponseEntity.ok(service.createCompilation);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilationById(@PathVariable Long compId) {
        service.deleteCompilationById(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<Compilation> updateCompilation(
            @Valid @RequestBody Compilation compilation,
            @PathVariable Long compId
    ) {
        return ResponseEntity.ok(service.updateCompilation(compilation, compId));
    }
}
