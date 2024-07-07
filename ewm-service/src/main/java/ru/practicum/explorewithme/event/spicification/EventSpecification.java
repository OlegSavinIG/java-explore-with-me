package ru.practicum.explorewithme.event.spicification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.explorewithme.event.model.EventEntity;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecification {
    public static Specification<EventEntity> hasUsers(List<Long> users) {
        return (root, query, criteriaBuilder) -> {
            if (users.isEmpty() || users == null) {
                return criteriaBuilder.conjunction();
            }
            return root.get("user").get("id").in(users);
        };
    }

    public static Specification<EventEntity> hasStates(List<String> states) {
        return (root, query, criteriaBuilder) -> {
            if (states.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("state").in(states);
        };
    }

    public static Specification<EventEntity> hasCategories(List<Long> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("category").get("id").in(categories);
        };
    }

    public static Specification<EventEntity> dateAfter(LocalDateTime rangeStart) {
        return (root, query, criteriaBuilder) -> {
            if (rangeStart == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), rangeStart);
        };
    }

    public static Specification<EventEntity> dateBefore(LocalDateTime rangeEnd) {
        return (root, query, criteriaBuilder) -> {
            if (rangeEnd == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("date"), rangeEnd);
        };
    }
}
