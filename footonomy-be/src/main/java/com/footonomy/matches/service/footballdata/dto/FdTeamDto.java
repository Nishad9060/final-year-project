package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdTeamDto(
        Long id,
        String name,
        String shortName,
        String crest) {

    public String externalId() {
        return String.valueOf(id);
    }
}
