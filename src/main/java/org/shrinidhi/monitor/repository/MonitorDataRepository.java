package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.MonitorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorDataRepository extends JpaRepository<MonitorData, Long> {
}