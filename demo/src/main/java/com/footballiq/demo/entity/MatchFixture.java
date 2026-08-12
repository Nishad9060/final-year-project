package com.footballiq.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_fixtures")
public class MatchFixture {

    @Id
    private String id; // ESPN match ID

    @Column(nullable = false)
    private String homeTeam;

    @Column(nullable = false)
    private String awayTeam;

    private String homeTeamLogo;

    private String awayTeamLogo;

    private Integer homeScore;

    private Integer awayScore;

    @Column(nullable = false)
    private String matchStatus; // e.g. "Scheduled", "Live", "Full Time"

    private String statusDetail; // e.g. "Fri, August 21st at 3:00 PM"

    @Column(nullable = false)
    private String league; // e.g. "eng.1"

    private String venue;

    private String matchDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public MatchFixture() {}

    // Getters and Setters for ALL fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHomeTeam() { return homeTeam; }
    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }

    public String getAwayTeam() { return awayTeam; }
    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }

    public String getHomeTeamLogo() { return homeTeamLogo; }
    public void setHomeTeamLogo(String homeTeamLogo) { this.homeTeamLogo = homeTeamLogo; }

    public String getAwayTeamLogo() { return awayTeamLogo; }
    public void setAwayTeamLogo(String awayTeamLogo) { this.awayTeamLogo = awayTeamLogo; }

    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }

    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }

    public String getMatchStatus() { return matchStatus; }
    public void setMatchStatus(String matchStatus) { this.matchStatus = matchStatus; }

    public String getStatusDetail() { return statusDetail; }
    public void setStatusDetail(String statusDetail) { this.statusDetail = statusDetail; }

    public String getLeague() { return league; }
    public void setLeague(String league) { this.league = league; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getMatchDate() { return matchDate; }
    public void setMatchDate(String matchDate) { this.matchDate = matchDate; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}
