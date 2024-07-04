package ru.practicum.explorewithme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Request DTO for statistic data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticRequest {
    @NotNull
    @NotBlank
    private String app;

    @NotNull
    @NotBlank
    private String uri;

    @NotNull
    @NotBlank
    private String ip;

    @NotNull
    private LocalDateTime creationTime;
}
