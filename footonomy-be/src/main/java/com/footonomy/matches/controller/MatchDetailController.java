package com.footonomy.matches.controller;

import com.footonomy.matches.entity.MatchDetail;
import com.footonomy.matches.entity.MatchEvent;
import com.footonomy.matches.entity.ShotEvent;
import com.footonomy.matches.repository.MatchDetailRepository;
import com.footonomy.matches.repository.MatchEventRepository;
import com.footonomy.matches.repository.ShotEventRepository;
import com.footonomy.matches.service.scraper.FootballDetailScraperService;
import com.footonomy.matches.service.scraper.UnderstatScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
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
    public ResponseEntity<Map<String, Object>> getMatchDetails(@PathVariable UUID matchId) {
        Map<String, Object> response = new HashMap<>();
        
        MatchDetail detail = matchDetailRepository.findById(matchId).orElse(null);
        List<MatchEvent> events = matchEventRepository.findByMatchIdOrderByMinuteAsc(matchId);
        
        response.put("details", detail);
        response.put("events", events);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{matchId}/shotmap")
    public ResponseEntity<List<ShotEvent>> getMatchShotmap(@PathVariable UUID matchId) {
        return ResponseEntity.ok(shotEventRepository.findByMatchIdOrderByMinuteAsc(matchId));
    }

    // Force scrape ESPN match details
    @PostMapping("/{matchId}/scrape-espn")
    public ResponseEntity<MatchDetail> scrapeEspnDetails(@PathVariable UUID matchId, @RequestParam String league, @RequestParam String espnMatchId) {
        MatchDetail detail = espnScraper.scrapeMatchDetails(league, matchId, espnMatchId);
        return ResponseEntity.ok(detail);
    }
    
    // Force scrape Understat shotmap
    @PostMapping("/{matchId}/scrape-understat")
    public ResponseEntity<String> scrapeUnderstatShots(@PathVariable UUID matchId, @RequestParam String understatMatchId) {
        understatScraper.scrapeMatchShots(matchId, understatMatchId);
        return ResponseEntity.ok("Understat data scraped successfully for match: " + matchId);
    }
}
