package org.shrinidhi.monitor.controller;

import org.shrinidhi.monitor.dto.MonitorDataDto;
import org.shrinidhi.monitor.entity.MonitorData;
import org.shrinidhi.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    // Health check endpoint
    @GetMapping("/health")
    public String healthCheck() {
        return "Monitor API is running";
    }

    // Accept new monitoring data
    @PostMapping("/monitor")
    public String monitorData(@Valid @RequestBody MonitorDataDto data) {
        return monitorService.processMonitorData(data);
    }

    // Fetch paginated, filtered logs (now with date range support)
    @GetMapping("/monitor")
    public Page<MonitorData> getAllMonitorData(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(page = 0, size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return monitorService.getFilteredLogs(level, serviceName, start, end, pageable);
    }



    // Delete by ID
    @DeleteMapping("/monitor/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        monitorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Bulk delete by date range
    @DeleteMapping("/monitor")
    public ResponseEntity<String> deleteByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) String level
    ) {
        long deleted;
        if (level != null) {
            deleted = monitorService.deleteByLevelAndTimestampBetween(level, start, end);
        } else {
            deleted = monitorService.deleteByTimestampBetween(start, end);
        }
        return ResponseEntity.ok(deleted + " log(s) deleted.");
    }
}
