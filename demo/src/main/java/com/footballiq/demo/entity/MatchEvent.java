package com.footballiq.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "match_events")
public class MatchEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private Integer minute;

    @Column(nullable = false)
    private String team; // "home" or "away"

    @Column(nullable = false)
    private String type; // "goal", "yellow_card", "red_card", "substitution"

    private String player;
    
    private String detail; // e.g., "Assist by X", or "Penalty"

    public MatchEvent() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public Integer getMinute() { return minute; }
    public void setMinute(Integer minute) { this.minute = minute; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
