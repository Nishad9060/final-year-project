package com.footonomy.matches.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "match_events")
@Getter
@Setter
public class MatchEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID matchId;

    @Column(nullable = false)
    private Integer minute;

    @Column(nullable = false)
    private String team; // "home" or "away", or team name

    @Column(nullable = false)
    private String type; // "goal", "yellow_card", "red_card", "substitution"

    private String player;
    
    private String detail; // e.g., "Assist by X", or "Penalty"
}
