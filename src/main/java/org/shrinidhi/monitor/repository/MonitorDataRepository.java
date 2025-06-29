package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.MonitorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Repository
public interface MonitorDataRepository extends JpaRepository<MonitorData, Long> {

    // Filter + Pagination support
    Page<MonitorData> findAllByLevelAndServiceName(String level, String serviceName, Pageable pageable);

    Page<MonitorData> findAllByLevel(String level, Pageable pageable);

    Page<MonitorData> findAllByServiceName(String serviceName, Pageable pageable);

    Page<MonitorData> findAll(Pageable pageable);

    // Date range support
    Page<MonitorData> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // Combined filters
    Page<MonitorData> findAllByLevelAndServiceNameAndTimestampBetween(
            String level, String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByLevelAndTimestampBetween(
            String level, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MonitorData> findAllByServiceNameAndTimestampBetween(
            String serviceName, LocalDateTime start, LocalDateTime end, Pageable pageable);

    long deleteByTimestampBetween(LocalDateTime start, LocalDateTime end);

    long deleteByLevelAndTimestampBetween(String level, LocalDateTime start, LocalDateTime end);
}
