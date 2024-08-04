package ru.practicum.explorewithme.user.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.compilation.model.CompilationRequest;
import ru.practicum.explorewithme.compilation.model.CompilationResponse;
import ru.practicum.explorewithme.user.service.admin.AdminCompilationService;

import javax.validation.Valid;

/**
 * REST controller for managing compilations by admin.
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCompilationController {

    /**
     * Service for managing compilations.
     */
    private final AdminCompilationService service;

    /**
     * Creates a new compilation.
     *
     * @param compilation the compilation request to create
     * @return the created compilation response
     */
    @PostMapping("/compilations")
    public ResponseEntity<CompilationResponse> createCompilation(
            @Valid @RequestBody final CompilationRequest compilation) {
        return new  ResponseEntity<>(
                service.createCompilation(compilation), HttpStatus.CREATED);
    }

    /**
     * Deletes a compilation by its ID.
     *
     * @param compId the ID of the compilation to delete
     */
    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilationById(@PathVariable final Integer compId) {
        service.deleteCompilationById(compId);
    }

    /**
     * Updates a compilation by its ID.
     *
     * @param compilation the compilation request with updated information
     * @param compId      the ID of the compilation to update
     * @return the updated compilation response
     */
    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationResponse> updateCompilation(
            @Valid @RequestBody final CompilationRequest compilation,
            @PathVariable final Integer compId) {
        return ResponseEntity.ok(service.updateCompilation(
                compilation, compId));
    }
}
