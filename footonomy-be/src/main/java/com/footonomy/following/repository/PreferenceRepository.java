package com.footonomy.following.repository;

import com.footonomy.following.entity.Preference;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {

    List<Preference> findByUserId(UUID userId);

    Optional<Preference> findByUserIdAndKey(UUID userId, String key);
}
