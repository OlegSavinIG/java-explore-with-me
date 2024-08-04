package ru.practicum.explorewithme.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.category.model.CategoryResponse;
import ru.practicum.explorewithme.user.model.UserResponseWithEvent;

import java.time.LocalDateTime;

/**
 * DTO for event responses.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    /**
     * The unique identifier of the event.
     */
    private Long id;

    /**
     * The annotation of the event.
     */
    private String annotation;

    /**
     * The description of the event.
     */
    private String description;

    /**
     * The creation date and time of the event.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    /**
     * The date and time when the event will take place.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * The publication date and time of the event.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    /**
     * Indicates if the event is paid.
     */
    private Boolean paid;

    /**
     * The number of confirmed requests for the event.
     */
    private Integer confirmedRequests;

    /**
     * The participant limit for the event.
     */
    private Integer participantLimit;

    /**
     * Indicates if the event requires request moderation.
     */
    private Boolean requestModeration;

    /**
     * The title of the event.
     */
    private String title;

    /**
     * The number of views of the event.
     */
    private Integer views;

    /**
     * The status of the event.
     */
    private EventStatus state;

    /**
     * The initiator of the event.
     */
    private UserResponseWithEvent initiator;

    /**
     * The category of the event.
     */
    private CategoryResponse category;
}
