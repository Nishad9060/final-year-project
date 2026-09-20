package com.footonomy.matches.repository;

import com.footonomy.matches.entity.Standing;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandingRepository extends JpaRepository<Standing, UUID> {

    List<Standing> findByTournamentIdOrderByPositionAsc(UUID tournamentId);

    void deleteByTournamentId(UUID tournamentId);
}
