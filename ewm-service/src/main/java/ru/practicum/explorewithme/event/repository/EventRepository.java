package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.explorewithme.event.model.EventEntity;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    Optional<Page<EventEntity>> findAllByUserId(Long userId, Pageable pageable);

    Optional<EventEntity> findByEventIdAndUserId(Long eventId, Long userId);
}
