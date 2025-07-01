package org.shrinidhi.monitor.entity;

import jakarta.persistence.*;

@Entity
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // If you add authentication later, add: private Long userId;

    private boolean darkMode = false;

    // Values: ERROR, WARN_ERROR, INFO_WARN_ERROR, ALL
    private String alertLevel = "ALL";

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isDarkMode() { return darkMode; }
    public void setDarkMode(boolean darkMode) { this.darkMode = darkMode; }

    public String getAlertLevel() { return alertLevel; }
    public void setAlertLevel(String alertLevel) { this.alertLevel = alertLevel; }
}
