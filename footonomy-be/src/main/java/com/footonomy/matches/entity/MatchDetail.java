package com.footonomy.matches.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "match_details")
@Getter
@Setter
public class MatchDetail {

    @Id
    private UUID id; // Matches the matches.id UUID

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

    @Column(length = 1000)
    private String homeStartingXi;

    @Column(length = 1000)
    private String awayStartingXi;

    private Double expectedGoalsHome;
    private Double expectedGoalsAway;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdatedAt = LocalDateTime.now();
    }
}
