package com.footonomy.matches.dto;

import com.footonomy.matches.entity.Tournament;
import java.util.UUID;

public record TournamentResponse(UUID id, String name, String season) {

    public static TournamentResponse from(Tournament tournament) {
        return new TournamentResponse(tournament.getId(), tournament.getName(), tournament.getSeason());
    }
}
