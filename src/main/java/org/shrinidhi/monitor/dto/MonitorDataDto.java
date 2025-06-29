package org.shrinidhi.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MonitorDataDto {

    @NotBlank
    private String serviceName;

    @NotBlank
    private String level;

    @NotBlank
    private String message;

    private LocalDateTime timestamp; //

    // Getters & Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
