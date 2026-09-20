package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdSeasonDto(String startDate, String endDate) {

    /** e.g. "2025/26", derived from the season start/end years. */
    public String label() {
        if (startDate == null || startDate.length() < 4) {
            return "unknown";
        }
        String startYear = startDate.substring(0, 4);
        String endYearShort = (endDate != null && endDate.length() >= 4)
                ? endDate.substring(2, 4)
                : String.valueOf((Integer.parseInt(startYear) + 1) % 100);
        return startYear + "/" + endYearShort;
    }
}
