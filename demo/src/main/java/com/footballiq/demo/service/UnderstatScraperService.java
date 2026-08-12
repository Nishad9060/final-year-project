package com.footballiq.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballiq.demo.entity.MatchDetail;
import com.footballiq.demo.entity.ShotEvent;
import com.footballiq.demo.repository.MatchDetailRepository;
import com.footballiq.demo.repository.ShotEventRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UnderstatScraperService {

    private final ShotEventRepository shotEventRepository;
    private final MatchDetailRepository matchDetailRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public UnderstatScraperService(ShotEventRepository shotEventRepository, MatchDetailRepository matchDetailRepository) {
        this.shotEventRepository = shotEventRepository;
        this.matchDetailRepository = matchDetailRepository;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public void scrapeMatchShots(String espnMatchId, String understatMatchId) {
        String url = "https://understat.com/match/" + understatMatchId;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String html = response.getBody();

            Pattern pattern = Pattern.compile("var shotsData \\= JSON.parse\\('([^']+)'\\)");
            Matcher matcher = pattern.matcher(html);
            
            if (matcher.find()) {
                String hexJson = matcher.group(1);
                // Decode hex string \x22 to "
                String jsonStr = hexStringToString(hexJson);
                
                JsonNode root = objectMapper.readTree(jsonStr);
                
                shotEventRepository.deleteByMatchId(espnMatchId);
                List<ShotEvent> shots = new ArrayList<>();
                
                // Parse Home Shots
                JsonNode homeShots = root.path("h");
                double homeXg = parseShots(homeShots, shots, espnMatchId, "home");
                
                // Parse Away Shots
                JsonNode awayShots = root.path("a");
                double awayXg = parseShots(awayShots, shots, espnMatchId, "away");
                
                shotEventRepository.saveAll(shots);
                
                // Update match detail with total xG
                MatchDetail detail = matchDetailRepository.findById(espnMatchId).orElse(new MatchDetail());
                detail.setId(espnMatchId);
                detail.setExpectedGoalsHome(homeXg);
                detail.setExpectedGoalsAway(awayXg);
                matchDetailRepository.save(detail);
                
                System.out.println("[Understat] Saved " + shots.size() + " shots and updated xG for match " + espnMatchId);
            }

        } catch (Exception e) {
            System.err.println("[Understat] Error scraping match " + understatMatchId + ": " + e.getMessage());
        }
    }
    
    private double parseShots(JsonNode shotsArray, List<ShotEvent> shots, String espnMatchId, String teamType) {
        double totalXg = 0.0;
        if (shotsArray.isArray()) {
            for (JsonNode s : shotsArray) {
                ShotEvent se = new ShotEvent();
                se.setMatchId(espnMatchId);
                se.setTeam(teamType);
                se.setMinute(s.path("minute").asInt());
                se.setPlayer(s.path("player").asText());
                se.setX(s.path("X").asDouble());
                se.setY(s.path("Y").asDouble());
                
                double xg = s.path("xG").asDouble();
                totalXg += xg;
                se.setXg(xg);
                
                se.setResult(s.path("result").asText());
                se.setSituation(s.path("situation").asText());
                se.setShotType(s.path("shotType").asText());
                
                shots.add(se);
            }
        }
        return Math.round(totalXg * 100.0) / 100.0; // Round to 2 decimal places
    }
    
    private String hexStringToString(String hexStr) {
        // Understat encodes its JSON in a very specific way: \x22 for quote, etc.
        // In Java, we can just unescape using basic replace for the common ones.
        return hexStr.replace("\\x22", "\"")
                     .replace("\\x20", " ")
                     .replace("\\x5C", "\\")
                     .replace("\\x27", "'")
                     .replace("\\x7B", "{")
                     .replace("\\x7D", "}")
                     .replace("\\x5B", "[")
                     .replace("\\x5D", "]")
                     .replace("\\x3A", ":")
                     .replace("\\x2C", ",");
    }
}
