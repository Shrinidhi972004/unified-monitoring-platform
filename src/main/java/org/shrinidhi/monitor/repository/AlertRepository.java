package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByIsReadFalseOrderByTimestampDesc();
    List<Alert> findAllByOrderByTimestampDesc();
}
