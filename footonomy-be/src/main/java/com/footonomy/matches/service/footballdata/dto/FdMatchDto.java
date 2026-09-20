package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdMatchDto(
        Long id,
        String utcDate,
        String status,
        FdTeamDto homeTeam,
        FdTeamDto awayTeam,
        FdScoreDto score) {

    public String externalId() {
        return String.valueOf(id);
    }
}
