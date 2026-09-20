package com.footonomy.following.service;

import com.footonomy.following.dto.SearchResponse;
import com.footonomy.matches.dto.TeamSummaryDto;
import com.footonomy.matches.dto.TournamentResponse;
import com.footonomy.matches.repository.TeamRepository;
import com.footonomy.matches.repository.TournamentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    public SearchResponse search(String query, String type) {
        String q = query == null ? "" : query.trim();
        boolean includeTeams = type == null || type.isBlank() || "team".equalsIgnoreCase(type);
        boolean includeTournaments = type == null || type.isBlank() || "tournament".equalsIgnoreCase(type);

        List<TeamSummaryDto> teams = (includeTeams && !q.isBlank())
                ? teamRepository.findByNameContainingIgnoreCaseOrShortNameContainingIgnoreCase(q, q).stream()
                        .map(TeamSummaryDto::from)
                        .toList()
                : List.of();

        List<TournamentResponse> tournaments = (includeTournaments && !q.isBlank())
                ? tournamentRepository.findByNameContainingIgnoreCase(q).stream()
                        .map(TournamentResponse::from)
                        .toList()
                : List.of();

        return SearchResponse.of(teams, tournaments);
    }
}
