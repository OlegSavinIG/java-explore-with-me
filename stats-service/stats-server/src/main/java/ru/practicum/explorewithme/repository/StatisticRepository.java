package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.StatisticEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link StatisticEntity} entities.
 */
@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {

    /**
     * Finds all statistics within the specified time range.
     *
     * @param start the start date and time
     * @param end   the end date and time
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.creationTime BETWEEN :start AND :end")
    List<StatisticEntity> findAllStatistic(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    /**
     * Finds all statistics for the specified URIs within the time range.
     *
     * @param start the start date and time
     * @param end   the end date and time
     * @param uris  the list of URIs
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.creationTime BETWEEN :start AND :end AND s.uri IN :uris")
    List<StatisticEntity> findAllStatisticByUris(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );

    /**
     * Finds all unique statistics within the specified time range.
     *
     * @param start the start date and time
     * @param end   the end date and time
     * @return the list of unique statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.creationTime BETWEEN :start AND :end GROUP BY s.ip, s.app, s.uri, s.creationTime")
    List<StatisticEntity> findAllStatisticWithUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    /**
     * Finds all unique statistics for the specified URIs within the time range.
     *
     * @param start the start date and time
     * @param end   the end date and time
     * @param uris  the list of URIs
     * @return the list of unique statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.creationTime BETWEEN :start AND :end AND s.uri IN :uris GROUP BY s.ip, s.app, s.uri, s.creationTime")
    List<StatisticEntity> findAllStatisticByUrisWithUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );
}
