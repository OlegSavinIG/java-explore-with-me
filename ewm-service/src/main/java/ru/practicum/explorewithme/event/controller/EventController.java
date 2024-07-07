package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventSearchCriteria;
import ru.practicum.explorewithme.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents(
            @ModelAttribute EventSearchCriteria criteria,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getEvents(criteria, from, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEvent(id));
    }
}
