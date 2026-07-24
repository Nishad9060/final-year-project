package com.footballiq.core.service;

import com.footballiq.adapter.FootballDataAdapter;
import com.footballiq.core.domain.Match;
import com.footballiq.core.domain.Momentum;
import com.footballiq.core.domain.PlayerHeatMap;
import com.footballiq.core.domain.Shot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final FootballDataAdapter footballDataAdapter;

    public MatchService(FootballDataAdapter footballDataAdapter) {
        this.footballDataAdapter = footballDataAdapter;
    }

    public List<Match> getLiveMatches() {
        return footballDataAdapter.getLiveMatches();
    }

    public List<Match> getMatchesByDate(String date) {
        return footballDataAdapter.getMatchesByDate(date);
    }

    public Optional<Match> getMatchDetails(String matchId) {
        return footballDataAdapter.getMatchDetails(matchId);
    }

    public List<Shot> getMatchShots(String matchId) {
        return footballDataAdapter.getMatchShots(matchId);
    }

    public List<Momentum> getMatchMomentum(String matchId) {
        return footballDataAdapter.getMatchMomentum(matchId);
    }

    public List<PlayerHeatMap> getPlayerHeatMap(String matchId, String playerId) {
        return footballDataAdapter.getPlayerHeatMap(matchId, playerId);
    }
}
