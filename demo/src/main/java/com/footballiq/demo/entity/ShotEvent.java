package com.footballiq.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shot_events")
public class ShotEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String matchId;

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

    public ShotEvent() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public Integer getMinute() { return minute; }
    public void setMinute(Integer minute) { this.minute = minute; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Double getXg() { return xg; }
    public void setXg(Double xg) { this.xg = xg; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }

    public String getShotType() { return shotType; }
    public void setShotType(String shotType) { this.shotType = shotType; }
}
