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
public class Momentum {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    private int minute;
    
    // Positive value favors home team, negative favors away team
    private int value; 
}
