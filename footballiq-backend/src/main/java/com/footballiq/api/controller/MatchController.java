package com.footballiq.api.controller;

import com.footballiq.core.domain.Match;
import com.footballiq.core.domain.Momentum;
import com.footballiq.core.domain.PlayerHeatMap;
import com.footballiq.core.domain.Shot;
import com.footballiq.core.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@CrossOrigin(origins = "*") // Allow flutter web/mobile during dev
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/live")
    public ResponseEntity<List<Match>> getLiveMatches() {
        return ResponseEntity.ok(matchService.getLiveMatches());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Match>> getMatchesByDate(@PathVariable String date) {
        return ResponseEntity.ok(matchService.getMatchesByDate(date));
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatchDetails(@PathVariable String matchId) {
        return matchService.getMatchDetails(matchId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{matchId}/shots")
    public ResponseEntity<List<Shot>> getMatchShots(@PathVariable String matchId) {
        return ResponseEntity.ok(matchService.getMatchShots(matchId));
    }

    @GetMapping("/{matchId}/momentum")
    public ResponseEntity<List<Momentum>> getMatchMomentum(@PathVariable String matchId) {
        return ResponseEntity.ok(matchService.getMatchMomentum(matchId));
    }

    @GetMapping("/{matchId}/heatmap/{playerId}")
    public ResponseEntity<List<PlayerHeatMap>> getPlayerHeatMap(
            @PathVariable String matchId,
            @PathVariable String playerId) {
        return ResponseEntity.ok(matchService.getPlayerHeatMap(matchId, playerId));
    }
}
