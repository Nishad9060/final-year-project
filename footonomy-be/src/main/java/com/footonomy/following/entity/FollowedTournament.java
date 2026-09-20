package com.footonomy.following.entity;

import com.footonomy.auth.entity.User;
import com.footonomy.common.entity.BaseEntity;
import com.footonomy.matches.entity.Tournament;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Real-FK replacement for the other half of the data dictionary's polymorphic Following table —
 * see docs/07_Data_Dictionary.md "Following" section and V1__init_schema.sql header note.
 */
@Entity
@Table(name = "followed_tournaments", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tournament_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowedTournament extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
}
