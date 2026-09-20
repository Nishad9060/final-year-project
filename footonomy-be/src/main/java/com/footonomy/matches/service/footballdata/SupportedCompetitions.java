package com.footonomy.matches.service.footballdata;

import java.util.List;

/**
 * football-data.org competition codes for the top 5 European leagues + Champions League
 * (docs/01_SRS.md §2, docs/CLAUDE.md). This is the single place to add/remove a league if
 * that open item (SRS §7) gets revisited — flag with the human before changing it.
 */
public final class SupportedCompetitions {

    public static final List<String> CODES = List.of(
            "PL",   // Premier League
            "PD",   // La Liga (Primera Division)
            "SA",   // Serie A
            "BL1",  // Bundesliga
            "FL1",  // Ligue 1
            "CL"    // UEFA Champions League
    );

    private SupportedCompetitions() {
    }
}
