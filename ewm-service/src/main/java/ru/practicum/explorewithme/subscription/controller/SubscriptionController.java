package ru.practicum.explorewithme.subscription.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.subscription.model.SubscriptionResponse;
import ru.practicum.explorewithme.subscription.service.SubscriptionService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping("/{subId}")
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @NotNull @PathVariable Long subId,
            @NotNull @RequestParam Long userId) {
        return ResponseEntity.ok(service.createSubscription(subId, userId));
    }

    @PatchMapping("/update/{subscriptionId}")
    public ResponseEntity<SubscriptionResponse> approveSubscription(
            @RequestParam String approve,
            @PathVariable Long subscriptionId) {
        return ResponseEntity.ok(service.approveSubscription(approve, subscriptionId));
    }

    @DeleteMapping
    public void deleteSubscription(@RequestParam Long subscriptionId) {
        service.deleteSubscription(subscriptionId);
    }

    @GetMapping("/{subId}")
    public ResponseEntity<List<EventResponse>> getFriendEvents(
            @PathVariable Long subId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(service.getFriendEvents(subId, userId));
    }

}
