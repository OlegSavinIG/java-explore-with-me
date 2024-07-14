package ru.practicum.explorewithme.subscription.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long subscriberId;
    private SubscriptionStatus status;
}
