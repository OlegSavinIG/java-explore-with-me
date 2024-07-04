package ru.practicum.explorewithme;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for statistic data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime creationTime;
}
