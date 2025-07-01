// src/main/java/org/shrinidhi/monitor/controller/AlertController.java
package org.shrinidhi.monitor.controller;

import org.shrinidhi.monitor.entity.Alert;
import org.shrinidhi.monitor.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    // Get all alerts (most recent first)
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAllByOrderByTimestampDesc();
    }

    // Get only unread alerts
    @GetMapping("/unread")
    public List<Alert> getUnreadAlerts() {
        return alertRepository.findByIsReadFalseOrderByTimestampDesc();
    }

    // Mark an alert as read
    @PatchMapping("/{id}/read")
    public Alert markAsRead(@PathVariable Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setRead(true);
        return alertRepository.save(alert);
    }
}
