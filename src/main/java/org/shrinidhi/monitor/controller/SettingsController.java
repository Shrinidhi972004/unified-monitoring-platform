package org.shrinidhi.monitor.controller;

import org.shrinidhi.monitor.entity.Settings;
import org.shrinidhi.monitor.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepository;

    // GET /api/settings (returns current settings; creates default if none exists)
    @GetMapping
    public Settings getSettings() {
        List<Settings> all = settingsRepository.findAll();
        if (all.isEmpty()) {
            // Create default settings if not present
            Settings defaults = new Settings();
            return settingsRepository.save(defaults);
        }
        return all.get(0); // For single-user, always return first
    }

    // POST /api/settings (update settings)
    @PostMapping
    public Settings updateSettings(@RequestBody Settings settings) {
        List<Settings> all = settingsRepository.findAll();
        if (all.isEmpty()) {
            return settingsRepository.save(settings);
        } else {
            Settings s = all.get(0);
            s.setDarkMode(settings.isDarkMode());
            s.setAlertLevel(settings.getAlertLevel());
            return settingsRepository.save(s);
        }
    }
}
