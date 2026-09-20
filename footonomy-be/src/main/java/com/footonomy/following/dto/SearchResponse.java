package com.footonomy.following.dto;

import com.footonomy.matches.dto.TeamSummaryDto;
import com.footonomy.matches.dto.TournamentResponse;
import java.util.List;

/**
 * Player results are always empty for V1: docs/07_Data_Dictionary.md defines no Player
 * entity, and docs/05_Risk_Register.md R8 / docs/01_SRS.md §7 direct falling back to
 * teams+tournaments only until player-data coverage from football-data.org is confirmed.
 */
public record SearchResponse(
        List<TeamSummaryDto> teams,
        List<TournamentResponse> tournaments,
        List<Object> players) {

    public static SearchResponse of(List<TeamSummaryDto> teams, List<TournamentResponse> tournaments) {
        return new SearchResponse(teams, tournaments, List.of());
    }
}
