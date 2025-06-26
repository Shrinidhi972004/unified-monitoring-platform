package org.shrinidhi.monitor.controller;

        import org.shrinidhi.monitor.dto.MonitorDataDto;
        import org.shrinidhi.monitor.entity.MonitorData;
        import org.shrinidhi.monitor.service.MonitorService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import jakarta.validation.Valid;
        import java.util.List;

        @RestController
        @RequestMapping("/api")
        public class MonitorController {

            @Autowired
            private MonitorService monitorService;

            @GetMapping("/health")
            public String healthCheck() {
                return "Monitor API is running";
            }

            @PostMapping("/monitor")
            public String monitorData(@Valid @RequestBody MonitorDataDto data) {
                return monitorService.processMonitorData(data);
            }

            @GetMapping("/monitor")
            public List<MonitorData> getAllMonitorData() {
                return monitorService.getAllMonitorData();
            }
        }