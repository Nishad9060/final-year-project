package com.footballiq.adapter;

import com.footballiq.core.domain.Match;
import com.footballiq.core.domain.Momentum;
import com.footballiq.core.domain.PlayerHeatMap;
import com.footballiq.core.domain.Shot;

import java.util.List;
import java.util.Optional;

public interface FootballDataAdapter {
    List<Match> getLiveMatches();
    List<Match> getMatchesByDate(String date); // format yyyy-MM-dd
    Optional<Match> getMatchDetails(String matchId);
    List<Shot> getMatchShots(String matchId);
    List<Momentum> getMatchMomentum(String matchId);
    List<PlayerHeatMap> getPlayerHeatMap(String matchId, String playerId);
    List<Standing> getStandings(String leagueId);
}
