package com.footonomy.matches.repository;

import com.footonomy.matches.entity.Team;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, UUID> {

    Optional<Team> findByExternalId(String externalId);

    List<Team> findByNameContainingIgnoreCaseOrShortNameContainingIgnoreCase(String name, String shortName);
}
