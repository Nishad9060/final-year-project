package com.footonomy.matches.controller;

import com.footonomy.matches.dto.MatchResponse;
import com.footonomy.matches.dto.StandingResponse;
import com.footonomy.matches.dto.TournamentResponse;
import com.footonomy.matches.service.TournamentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Public — no auth. See docs/03_TRD.md §4 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public List<TournamentResponse> getTournaments() {
        return tournamentService.listTournaments();
    }

    @GetMapping("/{id}")
    public TournamentResponse getTournament(@PathVariable UUID id) {
        return tournamentService.getTournamentById(id);
    }

    @GetMapping("/{id}/standings")
    public List<StandingResponse> getStandings(@PathVariable UUID id) {
        return tournamentService.getStandings(id);
    }

    @GetMapping("/{id}/fixtures")
    public List<MatchResponse> getFixtures(@PathVariable UUID id) {
        return tournamentService.getFixtures(id);
    }
}
