package com.footonomy.matches.repository;

import com.footonomy.matches.entity.Tournament;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    Optional<Tournament> findByExternalId(String externalId);

    List<Tournament> findByNameContainingIgnoreCase(String name);
}
