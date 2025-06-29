package org.shrinidhi.monitor.service;

import org.shrinidhi.monitor.dto.MonitorDataDto;
import org.shrinidhi.monitor.entity.MonitorData;
import org.shrinidhi.monitor.repository.MonitorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MonitorService {

    @Autowired
    private MonitorDataRepository repository;

    public String processMonitorData(MonitorDataDto data) {
        MonitorData entity = new MonitorData();
        entity.setServiceName(data.getServiceName());
        entity.setLevel(data.getLevel());
        entity.setMessage(data.getMessage());
        entity.setTimestamp(data.getTimestamp());
        repository.save(entity);
        return "Received and saved data: " + data.getMessage();
    }

    public Page<MonitorData> getFilteredLogs(
            String level,
            String serviceName,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable) {
        // If both start and end are provided, and other filters are present
        if (start != null && end != null) {
            if (level != null && serviceName != null) {
                return repository.findAllByLevelAndServiceNameAndTimestampBetween(level, serviceName, start, end, pageable);
            } else if (level != null) {
                return repository.findAllByLevelAndTimestampBetween(level, start, end, pageable);
            } else if (serviceName != null) {
                return repository.findAllByServiceNameAndTimestampBetween(serviceName, start, end, pageable);
            } else {
                return repository.findAllByTimestampBetween(start, end, pageable);
            }
        } else {
            // Fallback to existing logic (no date range)
            if (level != null && serviceName != null) {
                return repository.findAllByLevelAndServiceName(level, serviceName, pageable);
            } else if (level != null) {
                return repository.findAllByLevel(level, pageable);
            } else if (serviceName != null) {
                return repository.findAllByServiceName(serviceName, pageable);
            } else {
                return repository.findAll(pageable);
            }
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public long deleteByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return repository.deleteByTimestampBetween(start, end);
    }

    @Transactional
    public long deleteByLevelAndTimestampBetween(String level, LocalDateTime start, LocalDateTime end) {
        return repository.deleteByLevelAndTimestampBetween(level, start, end);
    }
}
