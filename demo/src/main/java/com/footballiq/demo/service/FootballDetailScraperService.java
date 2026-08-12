package com.footballiq.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballiq.demo.entity.MatchDetail;
import com.footballiq.demo.entity.MatchEvent;
import com.footballiq.demo.repository.MatchDetailRepository;
import com.footballiq.demo.repository.MatchEventRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FootballDetailScraperService {

    private final MatchDetailRepository matchDetailRepository;
    private final MatchEventRepository matchEventRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private static final String ESPN_SUMMARY_URL = "https://site.api.espn.com/apis/site/v2/sports/soccer/%s/summary?event=%s";

    public FootballDetailScraperService(MatchDetailRepository matchDetailRepository, MatchEventRepository matchEventRepository) {
        this.matchDetailRepository = matchDetailRepository;
        this.matchEventRepository = matchEventRepository;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public MatchDetail scrapeMatchDetails(String league, String matchId) {
        String url = String.format(ESPN_SUMMARY_URL, league, matchId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            MatchDetail detail = matchDetailRepository.findById(matchId).orElse(new MatchDetail());
            detail.setId(matchId);

            // Parse boxscore for stats & formations
            JsonNode boxscore = root.path("boxscore");
            JsonNode teams = boxscore.path("teams");
            
            if (teams.isArray()) {
                for (JsonNode teamNode : teams) {
                    String homeAway = teamNode.path("homeAway").asText();
                    JsonNode stats = teamNode.path("statistics");
                    
                    if (stats.isArray()) {
                        for (JsonNode stat : stats) {
                            String statName = stat.path("name").asText();
                            String displayValue = stat.path("displayValue").asText();
                            int value = 0;
                            try {
                                value = Integer.parseInt(displayValue.replaceAll("[^0-9]", ""));
                            } catch (NumberFormatException ignored) {}

                            if ("home".equals(homeAway)) {
                                switch (statName) {
                                    case "possessionPct": detail.setPossessionHome(value); break;
                                    case "shotsSummary": detail.setShotsHome(value); break;
                                    case "shotsOnGoal": detail.setShotsOnTargetHome(value); break;
                                    case "foulsCommitted": detail.setFoulsHome(value); break;
                                    case "wonCorners": detail.setCornersHome(value); break;
                                    case "yellowCards": detail.setYellowCardsHome(value); break;
                                    case "redCards": detail.setRedCardsHome(value); break;
                                }
                            } else {
                                switch (statName) {
                                    case "possessionPct": detail.setPossessionAway(value); break;
                                    case "shotsSummary": detail.setShotsAway(value); break;
                                    case "shotsOnGoal": detail.setShotsOnTargetAway(value); break;
                                    case "foulsCommitted": detail.setFoulsAway(value); break;
                                    case "wonCorners": detail.setCornersAway(value); break;
                                    case "yellowCards": detail.setYellowCardsAway(value); break;
                                    case "redCards": detail.setRedCardsAway(value); break;
                                }
                            }
                        }
                    }
                }
            }

            // Parse Formations and Lineups
            JsonNode rosters = root.path("rosters");
            if (rosters.isArray()) {
                for (JsonNode roster : rosters) {
                    String homeAway = roster.path("homeAway").asText();
                    String formation = roster.path("formation").asText("");
                    
                    StringBuilder lineupBuilder = new StringBuilder();
                    JsonNode rosterList = roster.path("roster");
                    if (rosterList.isArray()) {
                        for (JsonNode playerNode : rosterList) {
                            JsonNode athlete = playerNode.path("athlete");
                            String playerName = athlete.path("displayName").asText("");
                            boolean starter = playerNode.path("starter").asBoolean(false);
                            if (starter) {
                                if (lineupBuilder.length() > 0) lineupBuilder.append(", ");
                                lineupBuilder.append(playerName);
                            }
                        }
                    }
                    
                    if ("home".equals(homeAway)) {
                        detail.setHomeFormation(formation);
                        detail.setHomeStartingXI(lineupBuilder.toString());
                    } else {
                        detail.setAwayFormation(formation);
                        detail.setAwayStartingXI(lineupBuilder.toString());
                    }
                }
            }

            matchDetailRepository.save(detail);
            
            // Parse Key Events (Goals, Cards)
            matchEventRepository.deleteByMatchId(matchId);
            JsonNode keyEvents = root.path("keyEvents");
            List<MatchEvent> eventsToSave = new ArrayList<>();
            if (keyEvents.isArray()) {
                for (JsonNode evt : keyEvents) {
                    MatchEvent me = new MatchEvent();
                    me.setMatchId(matchId);
                    
                    JsonNode clock = evt.path("clock");
                    me.setMinute(clock.path("displayValue").asInt(0));
                    
                    JsonNode teamNode = evt.path("team");
                    // Need to determine home/away. For now just save team name.
                    // Or we could leave it simple
                    me.setTeam(teamNode.path("displayName").asText(""));
                    
                    String type = evt.path("type").path("text").asText("");
                    me.setType(type);
                    
                    JsonNode participants = evt.path("participants");
                    if (participants.isArray() && participants.size() > 0) {
                        me.setPlayer(participants.get(0).path("athlete").path("displayName").asText(""));
                    }
                    
                    me.setDetail(evt.path("text").asText(""));
                    eventsToSave.add(me);
                }
                matchEventRepository.saveAll(eventsToSave);
            }

            return detail;

        } catch (Exception e) {
            System.err.println("[DetailScraper] Error scraping match details for " + matchId + ": " + e.getMessage());
            return null;
        }
    }
}
