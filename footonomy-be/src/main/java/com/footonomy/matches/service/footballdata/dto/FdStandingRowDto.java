package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdStandingRowDto(
        Integer position,
        FdTeamDto team,
        Integer playedGames,
        Integer won,
        Integer draw,
        Integer lost,
        Integer points) {
}
