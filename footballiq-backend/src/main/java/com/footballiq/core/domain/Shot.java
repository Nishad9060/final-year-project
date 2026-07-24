package com.footballiq.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shot {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private int minute;
    private double x; // x coordinate on pitch (0-100)
    private double y; // y coordinate on pitch (0-100)
    private double expectedGoals; // xG
    private String result; // GOAL, SAVED, MISSED, BLOCKED
}
