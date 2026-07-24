package com.footballiq.api.controller;

import com.footballiq.core.domain.Standing;
import com.footballiq.core.service.StandingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/standings")
@CrossOrigin(origins = "*") // Allow flutter web/mobile during dev
public class StandingController {

    private final StandingService standingService;

    public StandingController(StandingService standingService) {
        this.standingService = standingService;
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<List<Standing>> getStandings(@PathVariable String leagueId) {
        return ResponseEntity.ok(standingService.getStandings(leagueId));
    }
}
