package com.footballiq.demo.controller;

import com.footballiq.demo.entity.MatchDetail;
import com.footballiq.demo.entity.MatchEvent;
import com.footballiq.demo.entity.ShotEvent;
import com.footballiq.demo.repository.MatchDetailRepository;
import com.footballiq.demo.repository.MatchEventRepository;
import com.footballiq.demo.repository.ShotEventRepository;
import com.footballiq.demo.service.FootballDetailScraperService;
import com.footballiq.demo.service.UnderstatScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fixtures")
public class MatchDetailController {

    private final MatchDetailRepository matchDetailRepository;
    private final MatchEventRepository matchEventRepository;
    private final ShotEventRepository shotEventRepository;
    private final FootballDetailScraperService espnScraper;
    private final UnderstatScraperService understatScraper;

    public MatchDetailController(MatchDetailRepository matchDetailRepository,
                                 MatchEventRepository matchEventRepository,
                                 ShotEventRepository shotEventRepository,
                                 FootballDetailScraperService espnScraper,
                                 UnderstatScraperService understatScraper) {
        this.matchDetailRepository = matchDetailRepository;
        this.matchEventRepository = matchEventRepository;
        this.shotEventRepository = shotEventRepository;
        this.espnScraper = espnScraper;
        this.understatScraper = understatScraper;
    }

    @GetMapping("/{matchId}/details")
    public ResponseEntity<Map<String, Object>> getMatchDetails(@PathVariable String matchId) {
        Map<String, Object> response = new HashMap<>();
        
        MatchDetail detail = matchDetailRepository.findById(matchId).orElse(null);
        List<MatchEvent> events = matchEventRepository.findByMatchIdOrderByMinuteAsc(matchId);
        
        response.put("details", detail);
        response.put("events", events);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{matchId}/shotmap")
    public ResponseEntity<List<ShotEvent>> getMatchShotmap(@PathVariable String matchId) {
        return ResponseEntity.ok(shotEventRepository.findByMatchIdOrderByMinuteAsc(matchId));
    }

    // Force scrape ESPN match details
    @PostMapping("/{matchId}/scrape-espn")
    public ResponseEntity<MatchDetail> scrapeEspnDetails(@PathVariable String matchId, @RequestParam String league) {
        MatchDetail detail = espnScraper.scrapeMatchDetails(league, matchId);
        return ResponseEntity.ok(detail);
    }
    
    // Force scrape Understat shotmap
    @PostMapping("/{espnMatchId}/scrape-understat")
    public ResponseEntity<String> scrapeUnderstatShots(@PathVariable String espnMatchId, @RequestParam String understatMatchId) {
        understatScraper.scrapeMatchShots(espnMatchId, understatMatchId);
        return ResponseEntity.ok("Understat data scraped successfully for match: " + espnMatchId);
    }
}
