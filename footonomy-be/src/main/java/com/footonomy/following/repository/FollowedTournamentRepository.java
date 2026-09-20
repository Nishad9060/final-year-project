package com.footonomy.following.repository;

import com.footonomy.following.entity.FollowedTournament;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowedTournamentRepository extends JpaRepository<FollowedTournament, UUID> {

    List<FollowedTournament> findByUserId(UUID userId);

    boolean existsByUserIdAndTournamentId(UUID userId, UUID tournamentId);

    Optional<FollowedTournament> findByIdAndUserId(UUID id, UUID userId);
}
