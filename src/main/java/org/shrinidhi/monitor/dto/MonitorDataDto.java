package org.shrinidhi.monitor.dto;

import jakarta.validation.constraints.NotBlank;

public class MonitorDataDto {

    @NotBlank
    private String serviceName;

    @NotBlank
    private String level;

    @NotBlank
    private String message;

    // Getters & Setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
