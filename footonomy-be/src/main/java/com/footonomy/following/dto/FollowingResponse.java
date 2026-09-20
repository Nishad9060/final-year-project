package com.footonomy.following.dto;

import com.footonomy.following.entity.FollowedTeam;
import com.footonomy.following.entity.FollowedTournament;
import com.footonomy.matches.dto.TeamSummaryDto;
import com.footonomy.matches.dto.TournamentResponse;
import java.util.UUID;

/**
 * Same external shape regardless of the internal split into followed_teams/followed_tournaments
 * (docs/07_Data_Dictionary.md Following note) — entityType/entityId is what the frozen API
 * contract (docs/03_TRD.md §4) exposed for the original polymorphic table.
 */
public record FollowingResponse(
        UUID id,
        String entityType,
        UUID entityId,
        TeamSummaryDto team,
        TournamentResponse tournament) {

    public static FollowingResponse fromTeam(FollowedTeam followedTeam) {
        return new FollowingResponse(
                followedTeam.getId(),
                "team",
                followedTeam.getTeam().getId(),
                TeamSummaryDto.from(followedTeam.getTeam()),
                null);
    }

    public static FollowingResponse fromTournament(FollowedTournament followedTournament) {
        return new FollowingResponse(
                followedTournament.getId(),
                "tournament",
                followedTournament.getTournament().getId(),
                null,
                TournamentResponse.from(followedTournament.getTournament()));
    }
}
