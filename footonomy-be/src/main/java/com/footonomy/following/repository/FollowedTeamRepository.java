package com.footonomy.following.repository;

import com.footonomy.following.entity.FollowedTeam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowedTeamRepository extends JpaRepository<FollowedTeam, UUID> {

    List<FollowedTeam> findByUserId(UUID userId);

    boolean existsByUserIdAndTeamId(UUID userId, UUID teamId);

    Optional<FollowedTeam> findByIdAndUserId(UUID id, UUID userId);
}
