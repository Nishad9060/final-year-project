package com.footballiq.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_details")
public class MatchDetail {

    @Id
    private String id; // Matches the MatchFixture ESPN ID

    private Integer possessionHome;
    private Integer possessionAway;

    private Integer shotsHome;
    private Integer shotsAway;

    private Integer shotsOnTargetHome;
    private Integer shotsOnTargetAway;

    private Integer foulsHome;
    private Integer foulsAway;

    private Integer cornersHome;
    private Integer cornersAway;

    private Integer yellowCardsHome;
    private Integer yellowCardsAway;

    private Integer redCardsHome;
    private Integer redCardsAway;

    private String homeFormation;
    private String awayFormation;

    private Double expectedGoalsHome;
    private Double expectedGoalsAway;

    @Column(length = 1000)
    private String homeStartingXI;

    @Column(length = 1000)
    private String awayStartingXI;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public MatchDetail() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getPossessionHome() { return possessionHome; }
    public void setPossessionHome(Integer possessionHome) { this.possessionHome = possessionHome; }

    public Integer getPossessionAway() { return possessionAway; }
    public void setPossessionAway(Integer possessionAway) { this.possessionAway = possessionAway; }

    public Integer getShotsHome() { return shotsHome; }
    public void setShotsHome(Integer shotsHome) { this.shotsHome = shotsHome; }

    public Integer getShotsAway() { return shotsAway; }
    public void setShotsAway(Integer shotsAway) { this.shotsAway = shotsAway; }

    public Integer getShotsOnTargetHome() { return shotsOnTargetHome; }
    public void setShotsOnTargetHome(Integer shotsOnTargetHome) { this.shotsOnTargetHome = shotsOnTargetHome; }

    public Integer getShotsOnTargetAway() { return shotsOnTargetAway; }
    public void setShotsOnTargetAway(Integer shotsOnTargetAway) { this.shotsOnTargetAway = shotsOnTargetAway; }

    public Integer getFoulsHome() { return foulsHome; }
    public void setFoulsHome(Integer foulsHome) { this.foulsHome = foulsHome; }

    public Integer getFoulsAway() { return foulsAway; }
    public void setFoulsAway(Integer foulsAway) { this.foulsAway = foulsAway; }

    public Integer getCornersHome() { return cornersHome; }
    public void setCornersHome(Integer cornersHome) { this.cornersHome = cornersHome; }

    public Integer getCornersAway() { return cornersAway; }
    public void setCornersAway(Integer cornersAway) { this.cornersAway = cornersAway; }

    public Integer getYellowCardsHome() { return yellowCardsHome; }
    public void setYellowCardsHome(Integer yellowCardsHome) { this.yellowCardsHome = yellowCardsHome; }

    public Integer getYellowCardsAway() { return yellowCardsAway; }
    public void setYellowCardsAway(Integer yellowCardsAway) { this.yellowCardsAway = yellowCardsAway; }

    public Integer getRedCardsHome() { return redCardsHome; }
    public void setRedCardsHome(Integer redCardsHome) { this.redCardsHome = redCardsHome; }

    public Integer getRedCardsAway() { return redCardsAway; }
    public void setRedCardsAway(Integer redCardsAway) { this.redCardsAway = redCardsAway; }

    public String getHomeFormation() { return homeFormation; }
    public void setHomeFormation(String homeFormation) { this.homeFormation = homeFormation; }

    public String getAwayFormation() { return awayFormation; }
    public void setAwayFormation(String awayFormation) { this.awayFormation = awayFormation; }

    public Double getExpectedGoalsHome() { return expectedGoalsHome; }
    public void setExpectedGoalsHome(Double expectedGoalsHome) { this.expectedGoalsHome = expectedGoalsHome; }

    public Double getExpectedGoalsAway() { return expectedGoalsAway; }
    public void setExpectedGoalsAway(Double expectedGoalsAway) { this.expectedGoalsAway = expectedGoalsAway; }

    public String getHomeStartingXI() { return homeStartingXI; }
    public void setHomeStartingXI(String homeStartingXI) { this.homeStartingXI = homeStartingXI; }

    public String getAwayStartingXI() { return awayStartingXI; }
    public void setAwayStartingXI(String awayStartingXI) { this.awayStartingXI = awayStartingXI; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}
