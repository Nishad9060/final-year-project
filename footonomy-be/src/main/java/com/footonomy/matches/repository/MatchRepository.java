package com.footonomy.matches.repository;

import com.footonomy.matches.entity.Match;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, UUID> {

    @Query("""
            select m from Match m
            where (:from is null or m.kickoffTime >= :from)
              and (:to is null or m.kickoffTime < :to)
              and (:tournamentId is null or m.tournament.id = :tournamentId)
            order by m.kickoffTime asc
            """)
    List<Match> search(
            @Param("from") Instant from,
            @Param("to") Instant to,
            @Param("tournamentId") UUID tournamentId);

    List<Match> findByTournamentIdOrderByKickoffTimeAsc(UUID tournamentId);

    /**
     * Natural key for a fixture, used to upsert during sync since the Data Dictionary's
     * Match table has no external_id column: two teams cannot play each other twice in the
     * same tournament at the same kickoff time.
     */
    Optional<Match> findByTournamentIdAndHomeTeamIdAndAwayTeamIdAndKickoffTime(
            UUID tournamentId, UUID homeTeamId, UUID awayTeamId, Instant kickoffTime);
}
