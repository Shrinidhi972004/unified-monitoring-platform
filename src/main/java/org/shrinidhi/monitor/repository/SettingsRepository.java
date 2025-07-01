package org.shrinidhi.monitor.repository;

import org.shrinidhi.monitor.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    // For multi-user: Settings findByUserId(Long userId);
}
