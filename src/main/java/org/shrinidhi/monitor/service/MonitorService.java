package org.shrinidhi.monitor.service;

import org.shrinidhi.monitor.dto.MonitorDataDto;
import org.shrinidhi.monitor.entity.MonitorData;
import org.shrinidhi.monitor.repository.MonitorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorService {

    @Autowired
    private MonitorDataRepository repository;

    public String processMonitorData(MonitorDataDto data) {
        MonitorData entity = new MonitorData();
        entity.setServiceName(data.getServiceName());
        entity.setLevel(data.getLevel());
        entity.setMessage(data.getMessage());
        repository.save(entity);
        return "Received and saved data: " + data.getMessage();
    }

    public List<MonitorData> getAllMonitorData() {
        return repository.findAll();
    }
}