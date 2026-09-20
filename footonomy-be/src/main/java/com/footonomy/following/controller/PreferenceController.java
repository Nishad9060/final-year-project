package com.footonomy.following.controller;

import com.footonomy.auth.security.CurrentUserProvider;
import com.footonomy.following.service.PreferenceService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Requires a valid JWT — see docs/03_TRD.md §4/§6 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/users/me/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public Map<String, String> getPreferences() {
        return preferenceService.getPreferences(currentUserProvider.requireCurrentUserId());
    }

    @PutMapping
    public Map<String, String> updatePreferences(@RequestBody Map<String, String> updates) {
        return preferenceService.updatePreferences(currentUserProvider.requireCurrentUserId(), updates);
    }
}
