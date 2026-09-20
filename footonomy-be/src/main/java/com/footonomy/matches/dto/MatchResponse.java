package com.footonomy.matches.dto;

import com.footonomy.matches.entity.Match;
import java.time.Instant;
import java.util.UUID;

public record MatchResponse(
        UUID id,
        UUID tournamentId,
        String tournamentName,
        TeamSummaryDto homeTeam,
        TeamSummaryDto awayTeam,
        Instant kickoffTime,
        String status,
        Integer homeScore,
        Integer awayScore,
        Instant lastSyncedAt) {

    public static MatchResponse from(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getTournament().getId(),
                match.getTournament().getName(),
                TeamSummaryDto.from(match.getHomeTeam()),
                TeamSummaryDto.from(match.getAwayTeam()),
                match.getKickoffTime(),
                match.getStatus().name(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getLastSyncedAt());
    }
}
