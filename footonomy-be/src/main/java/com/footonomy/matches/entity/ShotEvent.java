package com.footonomy.matches.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shot_events")
@Getter
@Setter
public class ShotEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID matchId;

    @Column(nullable = false)
    private Integer minute;

    @Column(nullable = false)
    private String team; // "home" or "away"

    private String player;

    private Double x; // 0.0 to 1.0 (Understat coordinate)
    private Double y; // 0.0 to 1.0

    private Double xg; // Expected goals value

    private String result; // "Goal", "Saved", "Missed", "Blocked", etc.

    private String situation; // "OpenPlay", "Penalty", "DirectFreekick", etc.
    
    private String shotType; // "RightFoot", "LeftFoot", "Head"
}
