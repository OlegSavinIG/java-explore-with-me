package ru.practicum.explorewithme.user.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.user.model.EventSearchCriteria;
import ru.practicum.explorewithme.user.service.admin.AdminEventService;

import java.util.List;

@RestController("/admin")
@RequiredArgsConstructor
public class AdminEventController {
    private final AdminEventService service;

    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getEvents(
            @ModelAttribute EventSearchCriteria criteria,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(service.getEvents(criteria, from, size));
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> approveEvent(
            @RequestBody EventRequest request,
            @PathVariable Long eventId) {
       EventResponse response = service.approveEvent(request, eventId);
        return ResponseEntity.ok(response);
    }
}
