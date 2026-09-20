package com.footonomy.matches.service;

import com.footonomy.common.exception.ResourceNotFoundException;
import com.footonomy.matches.dto.MatchResponse;
import com.footonomy.matches.repository.MatchRepository;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reads-only from the local DB. Never calls football-data.org directly — that's
 * {@link com.footonomy.matches.service.footballdata.FootballDataSyncJob}'s job.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {

    private final MatchRepository matchRepository;

    public List<MatchResponse> getMatches(String date, String league) {
        java.time.Instant from = null;
        java.time.Instant to = null;
        if (date != null && !date.isBlank()) {
            try {
                LocalDate parsed = LocalDate.parse(date);
                from = parsed.atStartOfDay(ZoneOffset.UTC).toInstant();
                to = parsed.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("date must be an ISO-8601 date (yyyy-MM-dd)");
            }
        }

        UUID tournamentId = null;
        if (league != null && !league.isBlank()) {
            try {
                tournamentId = UUID.fromString(league);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("league must be a valid tournament id");
            }
        }

        return matchRepository.search(from, to, tournamentId).stream()
                .map(MatchResponse::from)
                .toList();
    }

    public MatchResponse getMatchById(UUID id) {
        return matchRepository.findById(id)
                .map(MatchResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
    }
}
