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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;


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
    public Set<String> getAllLogLevels() {
        return repository.findAll()
                .stream()
                .map(MonitorData::getLevel)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllServiceNames() {
        return repository.findAll()
                .stream()
                .map(MonitorData::getServiceName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Page<MonitorData> getFilteredLogs(
            String level,
            String serviceName,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable) {

        boolean hasLevel = level != null && !level.isEmpty();
        boolean hasService = serviceName != null && !serviceName.isEmpty();
        boolean hasStart = start != null;
        boolean hasEnd = end != null;

        // --- Date range is present ---
        if (hasStart && hasEnd) {
            if (hasLevel && hasService) {
                // Partial level, partial service, date range
                return repository.findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCaseAndTimestampBetween(
                        level, serviceName, start, end, pageable);
            } else if (hasLevel) {
                // Partial level, date range
                return repository.findAllByLevelContainingIgnoreCaseAndTimestampBetween(
                        level, start, end, pageable);
            } else if (hasService) {
                // Partial service, date range
                return repository.findAllByServiceNameContainingIgnoreCaseAndTimestampBetween(
                        serviceName, start, end, pageable);
            } else {
                // Only date range
                return repository.findAllByTimestampBetween(start, end, pageable);
            }
        } else {
            // --- No date range ---
            if (hasLevel && hasService) {
                // Partial level, partial service
                return repository.findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCase(
                        level, serviceName, pageable);
            } else if (hasLevel) {
                // Partial level
                return repository.findAllByLevelContainingIgnoreCase(level, pageable);
            } else if (hasService) {
                // Partial service
                return repository.findAllByServiceNameContainingIgnoreCase(serviceName, pageable);
            } else {
                // No filters
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
