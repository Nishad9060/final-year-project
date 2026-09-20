package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdScoreDto(FdFullTimeDto fullTime) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FdFullTimeDto(Integer home, Integer away) {
    }
}
