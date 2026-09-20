package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdCompetitionDto(Long id, String name, String code) {

    public String externalId() {
        return String.valueOf(id);
    }
}
