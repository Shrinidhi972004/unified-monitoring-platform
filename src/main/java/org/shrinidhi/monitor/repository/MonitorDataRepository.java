package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.MonitorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Repository
public interface MonitorDataRepository extends JpaRepository<MonitorData, Long> {

    // Partial & case-insensitive search for serviceName
    Page<MonitorData> findAllByServiceNameContainingIgnoreCase(String serviceName, Pageable pageable);

    // Partial & case-insensitive search for level
    Page<MonitorData> findAllByLevelContainingIgnoreCase(String level, Pageable pageable);

    // Partial serviceName + partial level
    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCase(
            String level, String serviceName, Pageable pageable);

    // Partial serviceName + date range
    Page<MonitorData> findAllByServiceNameContainingIgnoreCaseAndTimestampBetween(
            String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Partial level + date range
    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndTimestampBetween(
            String level, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Partial level + serviceName + date range
    Page<MonitorData> findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCaseAndTimestampBetween(
            String level, String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // --- Legacy/Strict Match (optional, but useful for strict filter cases) ---
    Page<MonitorData> findAllByLevelAndServiceName(String level, String serviceName, Pageable pageable);

    Page<MonitorData> findAllByLevel(String level, Pageable pageable);

    Page<MonitorData> findAllByServiceName(String serviceName, Pageable pageable);

    Page<MonitorData> findAll(Pageable pageable);

    // Date range support (no level/service)
    Page<MonitorData> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Strict match + date range (legacy)
    Page<MonitorData> findAllByLevelAndTimestampBetween(
            String level, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByServiceNameAndTimestampBetween(
            String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByLevelAndServiceNameAndTimestampBetween(
            String level, String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // --- Delete operations ---
    long deleteByTimestampBetween(LocalDateTime start, LocalDateTime end);

    long deleteByLevelAndTimestampBetween(String level, LocalDateTime start, LocalDateTime end);
}
