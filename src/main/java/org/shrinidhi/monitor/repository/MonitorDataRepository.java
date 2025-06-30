package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.MonitorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonitorDataRepository extends JpaRepository<MonitorData, Long> {

    // --- PARTIAL, CASE-INSENSITIVE SEARCHES (for professional UI/UX filtering) ---
    Page<MonitorData> findAllByServiceNameContainingIgnoreCase(String serviceName, Pageable pageable);

    Page<MonitorData> findAllByLevelContainingIgnoreCase(String level, Pageable pageable);

    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCase(
            String level, String serviceName, Pageable pageable);

    Page<MonitorData> findAllByServiceNameContainingIgnoreCaseAndTimestampBetween(
            String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndTimestampBetween(
            String level, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCaseAndTimestampBetween(
            String level, String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // --- STRICT/EXACT MATCH (legacy support for possible edge cases) ---
    Page<MonitorData> findAllByLevelAndServiceName(String level, String serviceName, Pageable pageable);
    Page<MonitorData> findAllByLevel(String level, Pageable pageable);
    Page<MonitorData> findAllByServiceName(String serviceName, Pageable pageable);
    Page<MonitorData> findAll(Pageable pageable);

    // --- DATE RANGE ONLY ---
    Page<MonitorData> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<MonitorData> findAllByLevelAndTimestampBetween(String level, LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<MonitorData> findAllByServiceNameAndTimestampBetween(String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<MonitorData> findAllByLevelAndServiceNameAndTimestampBetween(String level, String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // --- DELETE OPERATIONS ---
    long deleteByTimestampBetween(LocalDateTime start, LocalDateTime end);
    long deleteByLevelAndTimestampBetween(String level, LocalDateTime start, LocalDateTime end);

    // --- AGGREGATION: Dashboard queries! ---
    @Query("SELECT m.serviceName as serviceName, COUNT(m) as count FROM MonitorData m GROUP BY m.serviceName")
    List<ServiceCountProjection> countGroupByService();

    @Query("SELECT m.level as level, COUNT(m) as count FROM MonitorData m GROUP BY m.level")
    List<LevelCountProjection> countGroupByLevel();
}
