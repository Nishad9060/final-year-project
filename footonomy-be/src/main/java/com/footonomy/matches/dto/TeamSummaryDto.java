package com.footonomy.matches.dto;

import com.footonomy.matches.entity.Team;
import java.util.UUID;

public record TeamSummaryDto(UUID id, String name, String shortName, String logoUrl) {

    public static TeamSummaryDto from(Team team) {
        return new TeamSummaryDto(team.getId(), team.getName(), team.getShortName(), team.getLogoUrl());
    }
}
