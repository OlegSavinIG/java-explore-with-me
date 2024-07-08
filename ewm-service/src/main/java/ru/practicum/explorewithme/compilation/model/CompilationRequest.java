package ru.practicum.explorewithme.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationRequest {
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
