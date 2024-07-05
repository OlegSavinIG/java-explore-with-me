package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.StatisticEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for accessing statistic data.
 */
@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {

    /**
     * Finds all statistics with
     * unique IPs between the specified start and end times.
     *
     * @param start the start time
     * @param end   the end time
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s" +
            " WHERE s.creationTime BETWEEN :start AND :end GROUP BY s.ip, s.uri")
    List<StatisticEntity> findAllStatisticWithUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    /**
     * Finds all statistics by URIs with unique
     * IPs between the specified start and end times.
     *
     * @param start the start time
     * @param end   the end time
     * @param uris  the list of URIs
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.uri IN :uris " +
            "AND s.creationTime BETWEEN :start AND :end GROUP BY s.ip, s.uri")
    List<StatisticEntity> findAllStatisticByUrisWithUniqueIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);

    /**
     * Finds all statistics between the specified start and end times.
     *
     * @param start the start time
     * @param end   the end time
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s " +
            "WHERE s.creationTime BETWEEN :start AND :end")
    List<StatisticEntity> findAllStatistic(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    /**
     * Finds all statistics by
     * URIs between the specified start and end times.
     *
     * @param start the start time
     * @param end   the end time
     * @param uris  the list of URIs
     * @return the list of statistics
     */
    @Query("SELECT s FROM StatisticEntity s WHERE s.uri IN :uris " +
            "AND s.creationTime BETWEEN :start AND :end")
    List<StatisticEntity> findAllStatisticByUris(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);
}
