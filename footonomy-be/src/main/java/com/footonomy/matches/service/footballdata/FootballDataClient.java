package com.footonomy.matches.service.footballdata;

import com.footonomy.matches.service.footballdata.dto.FdMatchesResponse;
import com.footonomy.matches.service.footballdata.dto.FdStandingsResponse;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Thin wrapper around football-data.org v4. Every call goes through {@link FootballDataRateLimiter}
 * first — nothing in this class may be called directly from a controller (docs/03_TRD.md §5).
 */
@Slf4j
@Component
public class FootballDataClient {

    private final RestClient restClient;
    private final FootballDataRateLimiter rateLimiter;

    public FootballDataClient(
            @Value("${football-data.api.base-url}") String baseUrl,
            @Value("${football-data.api.token}") String apiToken,
            FootballDataRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-Auth-Token", apiToken)
                .build();
    }

    public FdStandingsResponse getStandings(String competitionCode) {
        rateLimiter.acquire();
        return restClient.get()
                .uri("/competitions/{code}/standings", competitionCode)
                .retrieve()
                .body(FdStandingsResponse.class);
    }

    public FdMatchesResponse getMatches(String competitionCode) {
        rateLimiter.acquire();
        LocalDate from = LocalDate.now().minusDays(3);
        LocalDate to = LocalDate.now().plusDays(14);
        return restClient.get()
                .uri("/competitions/{code}/matches?dateFrom={from}&dateTo={to}", competitionCode, from, to)
                .retrieve()
                .body(FdMatchesResponse.class);
    }
}
