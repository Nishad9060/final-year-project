package com.footonomy.matches.service.footballdata;

import com.footonomy.matches.entity.Match;
import com.footonomy.matches.entity.MatchStatus;
import com.footonomy.matches.entity.Standing;
import com.footonomy.matches.entity.Team;
import com.footonomy.matches.entity.Tournament;
import com.footonomy.matches.repository.MatchRepository;
import com.footonomy.matches.repository.StandingRepository;
import com.footonomy.matches.repository.TeamRepository;
import com.footonomy.matches.repository.TournamentRepository;
import com.footonomy.matches.service.footballdata.dto.FdMatchDto;
import com.footonomy.matches.service.footballdata.dto.FdMatchesResponse;
import com.footonomy.matches.service.footballdata.dto.FdStandingRowDto;
import com.footonomy.matches.service.footballdata.dto.FdStandingsResponse;
import com.footonomy.matches.service.footballdata.dto.FdTeamDto;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pulls fixtures/standings from football-data.org and upserts them into local storage.
 * Never called from a controller — controllers only ever read from the DB (docs/03_TRD.md §5).
 * A failure syncing one competition is logged and skipped so the rest of the cycle still runs;
 * callers (the sync job) already treat "nothing changed this cycle" as success, which is what
 * naturally delivers the "serve last-cached data" failure mode required by the TRD.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FootballDataSyncService {

    private final FootballDataClient client;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;

    @Transactional
    public void syncCompetition(String competitionCode) {
        FdStandingsResponse standingsResponse = client.getStandings(competitionCode);
        Tournament tournament = upsertTournament(standingsResponse);
        upsertStandings(tournament, standingsResponse);

        FdMatchesResponse matchesResponse = client.getMatches(competitionCode);
        upsertMatches(tournament, matchesResponse);
    }

    private Tournament upsertTournament(FdStandingsResponse response) {
        String externalId = response.competition().externalId();
        Tournament tournament = tournamentRepository.findByExternalId(externalId)
                .orElseGet(Tournament::new);
        tournament.setExternalId(externalId);
        tournament.setName(response.competition().name());
        tournament.setSeason(response.season() != null ? response.season().label() : "unknown");
        return tournamentRepository.save(tournament);
    }

    private void upsertStandings(Tournament tournament, FdStandingsResponse response) {
        if (response.standings() == null) {
            return;
        }
        response.standings().stream()
                .filter(table -> "TOTAL".equalsIgnoreCase(table.type()))
                .findFirst()
                .ifPresent(table -> {
                    standingRepository.deleteByTournamentId(tournament.getId());
                    for (FdStandingRowDto row : table.table()) {
                        Team team = upsertTeam(row.team());
                        Standing standing = new Standing();
                        standing.setTournament(tournament);
                        standing.setTeam(team);
                        standing.setPosition(row.position());
                        standing.setPlayed(row.playedGames());
                        standing.setWon(row.won());
                        standing.setDrawn(row.draw());
                        standing.setLost(row.lost());
                        standing.setPoints(row.points());
                        standingRepository.save(standing);
                    }
                });
    }

    private void upsertMatches(Tournament tournament, FdMatchesResponse response) {
        if (response.matches() == null) {
            return;
        }
        for (FdMatchDto dto : response.matches()) {
            Instant kickoff;
            try {
                kickoff = Instant.parse(dto.utcDate());
            } catch (DateTimeParseException | NullPointerException e) {
                log.warn("Skipping match {} with unparseable kickoff date", dto.id());
                continue;
            }

            Team homeTeam = upsertTeam(dto.homeTeam());
            Team awayTeam = upsertTeam(dto.awayTeam());

            Match match = matchRepository
                    .findByTournamentIdAndHomeTeamIdAndAwayTeamIdAndKickoffTime(
                            tournament.getId(), homeTeam.getId(), awayTeam.getId(), kickoff)
                    .orElseGet(Match::new);

            match.setTournament(tournament);
            match.setHomeTeam(homeTeam);
            match.setAwayTeam(awayTeam);
            match.setKickoffTime(kickoff);
            match.setStatus(mapStatus(dto.status()));
            if (dto.score() != null && dto.score().fullTime() != null) {
                match.setHomeScore(dto.score().fullTime().home());
                match.setAwayScore(dto.score().fullTime().away());
            }
            match.setLastSyncedAt(Instant.now());
            matchRepository.save(match);
        }
    }

    private Team upsertTeam(FdTeamDto dto) {
        Team team = teamRepository.findByExternalId(dto.externalId()).orElseGet(Team::new);
        team.setExternalId(dto.externalId());
        team.setName(dto.name());
        team.setShortName(dto.shortName());
        team.setLogoUrl(dto.crest());
        return teamRepository.save(team);
    }

    private MatchStatus mapStatus(String fdStatus) {
        if (fdStatus == null) {
            return MatchStatus.scheduled;
        }
        return switch (fdStatus) {
            case "IN_PLAY", "PAUSED" -> MatchStatus.live;
            case "FINISHED" -> MatchStatus.finished;
            default -> MatchStatus.scheduled;
        };
    }
}
