package com.footballiq.core.service;

import com.footballiq.adapter.FootballDataAdapter;
import com.footballiq.core.domain.Standing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingService {

    private final FootballDataAdapter footballDataAdapter;

    public StandingService(FootballDataAdapter footballDataAdapter) {
        this.footballDataAdapter = footballDataAdapter;
    }

    public List<Standing> getStandings(String leagueId) {
        return footballDataAdapter.getStandings(leagueId);
    }
}
