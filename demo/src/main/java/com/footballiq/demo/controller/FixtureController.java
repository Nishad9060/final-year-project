package com.footballiq.demo.controller;

import com.footballiq.demo.entity.MatchFixture;
import com.footballiq.demo.service.FixtureService;
import com.footballiq.demo.service.FootballScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {

    private final FixtureService fixtureService;
    private final FootballScraperService footballScraperService;

    public FixtureController(FixtureService fixtureService, FootballScraperService footballScraperService) {
        this.fixtureService = fixtureService;
        this.footballScraperService = footballScraperService;
    }

    @GetMapping
    public ResponseEntity<List<MatchFixture>> getAllFixtures() {
        return ResponseEntity.ok(fixtureService.getAllFixtures());
    }

    @GetMapping("/league/{league}")
    public ResponseEntity<List<MatchFixture>> getFixturesByLeague(@PathVariable String league) {
        return ResponseEntity.ok(fixtureService.getFixturesByLeague(league));
    }

    @PostMapping("/sync-now")
    public ResponseEntity<Map<String, Object>> syncNow() {
        List<MatchFixture> scraped = footballScraperService.scrapeAllLeagues();
        fixtureService.evictAllCaches();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Scrape complete");
        response.put("fixturesScraped", scraped.size());
        return ResponseEntity.ok(response);
    }
}
