package ru.practicum.explorewithme.compilation
        ;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.compilation.model.CompilationResponse;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService service;
    @GetMapping
    public ResponseEntity<List<CompilationResponse>> getCompilations(
            @RequestParam(defaultValue = "false") Boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {

    }
}
