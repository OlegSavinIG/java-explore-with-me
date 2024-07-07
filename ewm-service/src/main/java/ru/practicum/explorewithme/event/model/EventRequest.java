package ru.practicum.explorewithme.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Integer category;
    private String stateAction;
}
