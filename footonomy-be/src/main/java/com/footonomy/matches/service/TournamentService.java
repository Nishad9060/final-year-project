package com.footonomy.matches.service;

import com.footonomy.common.exception.ResourceNotFoundException;
import com.footonomy.matches.dto.MatchResponse;
import com.footonomy.matches.dto.StandingResponse;
import com.footonomy.matches.dto.TournamentResponse;
import com.footonomy.matches.repository.MatchRepository;
import com.footonomy.matches.repository.StandingRepository;
import com.footonomy.matches.repository.TournamentRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final StandingRepository standingRepository;
    private final MatchRepository matchRepository;

    public List<TournamentResponse> listTournaments() {
        return tournamentRepository.findAll().stream().map(TournamentResponse::from).toList();
    }

    public TournamentResponse getTournamentById(UUID id) {
        return tournamentRepository.findById(id)
                .map(TournamentResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found: " + id));
    }

    public List<StandingResponse> getStandings(UUID tournamentId) {
        assertTournamentExists(tournamentId);
        return standingRepository.findByTournamentIdOrderByPositionAsc(tournamentId).stream()
                .map(StandingResponse::from)
                .toList();
    }

    public List<MatchResponse> getFixtures(UUID tournamentId) {
        assertTournamentExists(tournamentId);
        return matchRepository.findByTournamentIdOrderByKickoffTimeAsc(tournamentId).stream()
                .map(MatchResponse::from)
                .toList();
    }

    private void assertTournamentExists(UUID tournamentId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new ResourceNotFoundException("Tournament not found: " + tournamentId);
        }
    }
}
