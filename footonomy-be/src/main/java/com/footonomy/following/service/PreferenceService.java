package com.footonomy.following.service;

import com.footonomy.auth.repository.UserRepository;
import com.footonomy.following.entity.Preference;
import com.footonomy.following.repository.PreferenceRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Map<String, String> getPreferences(UUID userId) {
        Map<String, String> result = new HashMap<>();
        preferenceRepository.findByUserId(userId).forEach(p -> result.put(p.getKey(), p.getValue()));
        return result;
    }

    @Transactional
    public Map<String, String> updatePreferences(UUID userId, Map<String, String> updates) {
        for (Map.Entry<String, String> entry : updates.entrySet()) {
            Preference preference = preferenceRepository.findByUserIdAndKey(userId, entry.getKey())
                    .orElseGet(() -> {
                        Preference p = new Preference();
                        p.setUser(userRepository.getReferenceById(userId));
                        p.setKey(entry.getKey());
                        return p;
                    });
            preference.setValue(entry.getValue());
            preferenceRepository.save(preference);
        }
        return getPreferences(userId);
    }
}
