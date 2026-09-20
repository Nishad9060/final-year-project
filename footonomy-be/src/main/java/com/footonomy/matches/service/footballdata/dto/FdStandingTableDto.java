package com.footonomy.matches.service.footballdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FdStandingTableDto(String type, List<FdStandingRowDto> table) {
}
