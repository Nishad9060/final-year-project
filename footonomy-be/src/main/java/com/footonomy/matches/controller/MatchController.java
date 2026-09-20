package com.footonomy.matches.controller;

import com.footonomy.matches.dto.MatchResponse;
import com.footonomy.matches.service.MatchService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Public — no auth. See docs/03_TRD.md §4 and docs/CLAUDE.md hard constraints. */
@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public List<MatchResponse> getMatches(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String league) {
        return matchService.getMatches(date, league);
    }

    @GetMapping("/{id}")
    public MatchResponse getMatch(@PathVariable UUID id) {
        return matchService.getMatchById(id);
    }
}
