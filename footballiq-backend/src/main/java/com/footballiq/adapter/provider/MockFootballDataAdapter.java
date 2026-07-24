package com.footballiq.adapter.provider;

import com.footballiq.adapter.FootballDataAdapter;
import com.footballiq.core.domain.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MockFootballDataAdapter implements FootballDataAdapter {

    private final Team teamA = new Team("t1", "Real Madrid", "https://example.com/rm.png", "RMA");
    private final Team teamB = new Team("t2", "Barcelona", "https://example.com/fcb.png", "BAR");
    private final Player player1 = new Player("p1", "Vinicius Jr", "Forward", teamA);
    private final Player player2 = new Player("p2", "Lamine Yamal", "Forward", teamB);

    private final Match liveMatch = Match.builder()
            .id("m1")
            .homeTeam(teamA)
            .awayTeam(teamB)
            .startTime(ZonedDateTime.now().minusMinutes(45))
            .status("LIVE")
            .homeScore(2)
            .awayScore(1)
            .minute(45)
            .build();

    @Override
    public List<Match> getLiveMatches() {
        return List.of(liveMatch);
    }

    @Override
    public List<Match> getMatchesByDate(String date) {
        return List.of(liveMatch);
    }

    @Override
    public Optional<Match> getMatchDetails(String matchId) {
        if ("m1".equals(matchId)) {
            return Optional.of(liveMatch);
        }
        return Optional.empty();
    }

    @Override
    public List<Shot> getMatchShots(String matchId) {
        return List.of(
                new Shot(UUID.randomUUID().toString(), liveMatch, player1, 12, 80.0, 50.0, 0.15, "GOAL"),
                new Shot(UUID.randomUUID().toString(), liveMatch, player2, 34, 15.0, 45.0, 0.05, "SAVED"),
                new Shot(UUID.randomUUID().toString(), liveMatch, player1, 42, 85.0, 55.0, 0.40, "GOAL")
        );
    }

    @Override
    public List<Momentum> getMatchMomentum(String matchId) {
        List<Momentum> momentumList = new ArrayList<>();
        for (int i = 0; i <= 45; i++) {
            int value = (int) (Math.random() * 100) - 50; // Random value between -50 and 50
            momentumList.add(new Momentum(UUID.randomUUID().toString(), liveMatch, i, value));
        }
        return momentumList;
    }

    @Override
    public List<PlayerHeatMap> getPlayerHeatMap(String matchId, String playerId) {
        List<PlayerHeatMap> heatMaps = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            double x = Math.random() * 100;
            double y = Math.random() * 100;
            int intensity = (int) (Math.random() * 10) + 1;
            heatMaps.add(new PlayerHeatMap(UUID.randomUUID().toString(), liveMatch, player1, x, y, intensity));
        }
        return heatMaps;
    }

    @Override
    public List<Standing> getStandings(String leagueId) {
        return List.of(
            new Standing(UUID.randomUUID().toString(), leagueId, teamA, 1, 85, 34, 27, 4, 3, 80, 25, 55),
            new Standing(UUID.randomUUID().toString(), leagueId, teamB, 2, 79, 34, 24, 7, 3, 70, 30, 40)
        );
    }
}
