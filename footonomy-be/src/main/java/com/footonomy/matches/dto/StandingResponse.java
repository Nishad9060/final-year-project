package com.footonomy.matches.dto;

import com.footonomy.matches.entity.Standing;

public record StandingResponse(
        TeamSummaryDto team,
        Integer position,
        Integer played,
        Integer won,
        Integer drawn,
        Integer lost,
        Integer points) {

    public static StandingResponse from(Standing standing) {
        return new StandingResponse(
                TeamSummaryDto.from(standing.getTeam()),
                standing.getPosition(),
                standing.getPlayed(),
                standing.getWon(),
                standing.getDrawn(),
                standing.getLost(),
                standing.getPoints());
    }
}
