package ru.practicum.explorewithme.user.controller.privateuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.user.service.privateuser.PrivateUserEventsService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/users")
@RequiredArgsConstructor
public class PrivateUserEventsController {
    private final PrivateUserEventsService service;

    @GetMapping("/{userId}/events")
    public ResponseEntity<List<EventResponse>> getEventsByUserId(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getEventsByUserId(userId, from, size));
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<List<EventResponse>> getByUserIdAndEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(service.getByUserIdAndEventId(userId, eventId));
    }


    @PostMapping("/{userId}/events")
    public ResponseEntity<EventResponse> createEvent(
            @PathVariable Long userId,
            @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(service.createEvent(request, userId));
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequest request) {
        return ResponseEntity.ok(service.updateEntity(userId, eventId, request));
    }


}
