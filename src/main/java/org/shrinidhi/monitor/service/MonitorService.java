package org.shrinidhi.monitor.service;

import org.shrinidhi.monitor.dto.MonitorDataDto;
import org.shrinidhi.monitor.entity.MonitorData;
import org.shrinidhi.monitor.repository.MonitorDataRepository;
import org.shrinidhi.monitor.repository.ServiceCountProjection;
import org.shrinidhi.monitor.repository.LevelCountProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    // For dropdowns
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

        if (hasStart && hasEnd) {
            if (hasLevel && hasService) {
                return repository.findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCaseAndTimestampBetween(
                        level, serviceName, start, end, pageable);
            } else if (hasLevel) {
                return repository.findAllByLevelContainingIgnoreCaseAndTimestampBetween(
                        level, start, end, pageable);
            } else if (hasService) {
                return repository.findAllByServiceNameContainingIgnoreCaseAndTimestampBetween(
                        serviceName, start, end, pageable);
            } else {
                return repository.findAllByTimestampBetween(start, end, pageable);
            }
        } else {
            if (hasLevel && hasService) {
                return repository.findAllByLevelContainingIgnoreCaseAndServiceNameContainingIgnoreCase(
                        level, serviceName, pageable);
            } else if (hasLevel) {
                return repository.findAllByLevelContainingIgnoreCase(level, pageable);
            } else if (hasService) {
                return repository.findAllByServiceNameContainingIgnoreCase(serviceName, pageable);
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

    // Dashboard methods

    public long getTotalLogCount() {
        return repository.count();
    }

    public List<Map<String, Object>> getLogCountByService() {
        List<ServiceCountProjection> projectionList = repository.countGroupByService();
        return projectionList.stream()
                .map(proj -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("serviceName", proj.getServiceName());
                    map.put("count", proj.getCount());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getLogCountByLevel() {
        List<LevelCountProjection> projectionList = repository.countGroupByLevel();
        return projectionList.stream()
                .map(proj -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("level", proj.getLevel());
                    map.put("count", proj.getCount());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
