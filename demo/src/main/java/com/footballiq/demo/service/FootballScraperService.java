package com.footballiq.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballiq.demo.entity.MatchFixture;
import com.footballiq.demo.repository.MatchFixtureRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FootballScraperService {

    private final MatchFixtureRepository matchFixtureRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    // Leagues to scrape from ESPN's free API
    private static final String[] LEAGUES = {
            "eng.1",   // Premier League
            "esp.1",   // La Liga
            "ger.1",   // Bundesliga
            "ita.1",   // Serie A
            "fra.1",   // Ligue 1
            "uefa.champions"  // Champions League
    };

    private static final String ESPN_BASE_URL = "https://site.api.espn.com/apis/site/v2/sports/soccer/%s/scoreboard";

    public FootballScraperService(MatchFixtureRepository matchFixtureRepository) {
        this.matchFixtureRepository = matchFixtureRepository;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    public List<MatchFixture> scrapeAllLeagues() {
        List<MatchFixture> allFixtures = new ArrayList<>();
        for (String league : LEAGUES) {
            try {
                List<MatchFixture> fixtures = scrapeLeague(league);
                allFixtures.addAll(fixtures);
                System.out.println("[Scraper] Fetched " + fixtures.size() + " matches from " + league);
            } catch (Exception e) {
                System.err.println("[Scraper] Failed to scrape " + league + ": " + e.getMessage());
            }
        }
        if (!allFixtures.isEmpty()) {
            matchFixtureRepository.saveAll(allFixtures);
            System.out.println("[Scraper] Total saved to DB: " + allFixtures.size() + " fixtures");
        }
        return allFixtures;
    }

    public List<MatchFixture> scrapeLeague(String league) throws Exception {
        String url = String.format(ESPN_BASE_URL, league);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String json = response.getBody();

        JsonNode root = objectMapper.readTree(json);
        JsonNode events = root.path("events");

        List<MatchFixture> fixtures = new ArrayList<>();

        if (events.isArray()) {
            for (JsonNode event : events) {
                try {
                    MatchFixture fixture = parseEvent(event, league);
                    if (fixture != null) {
                        fixtures.add(fixture);
                    }
                } catch (Exception e) {
                    System.err.println("[Scraper] Error parsing event: " + e.getMessage());
                }
            }
        }

        return fixtures;
    }

    private MatchFixture parseEvent(JsonNode event, String league) {
        MatchFixture fixture = new MatchFixture();
        fixture.setId(event.path("id").asText());
        fixture.setLeague(league);

        // Status
        JsonNode status = event.path("status").path("type");
        fixture.setMatchStatus(status.path("description").asText("Unknown"));
        fixture.setStatusDetail(status.path("detail").asText(""));

        // Date
        fixture.setMatchDate(event.path("date").asText(""));

        // Venue
        if (event.has("venue")) {
            fixture.setVenue(event.path("venue").path("displayName").asText(""));
        }

        // Competitors (inside competitions array)
        JsonNode competitions = event.path("competitions");
        if (competitions.isArray() && competitions.size() > 0) {
            JsonNode competition = competitions.get(0);
            JsonNode competitors = competition.path("competitors");

            if (competitors.isArray()) {
                for (JsonNode competitor : competitors) {
                    String homeAway = competitor.path("homeAway").asText();
                    JsonNode team = competitor.path("team");
                    String teamName = team.path("displayName").asText("Unknown");
                    String teamLogo = team.path("logo").asText("");
                    int score = 0;
                    try {
                        score = Integer.parseInt(competitor.path("score").asText("0"));
                    } catch (NumberFormatException ignored) {}

                    if ("home".equals(homeAway)) {
                        fixture.setHomeTeam(teamName);
                        fixture.setHomeTeamLogo(teamLogo);
                        fixture.setHomeScore(score);
                    } else {
                        fixture.setAwayTeam(teamName);
                        fixture.setAwayTeamLogo(teamLogo);
                        fixture.setAwayScore(score);
                    }
                }
            }

            // Venue from competition level if not set at event level
            if (fixture.getVenue() == null || fixture.getVenue().isEmpty()) {
                JsonNode compVenue = competition.path("venue");
                if (compVenue.has("fullName")) {
                    fixture.setVenue(compVenue.path("fullName").asText(""));
                }
            }
        }

        return fixture;
    }
}
