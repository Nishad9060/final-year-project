package com.footonomy.following.entity;

import com.footonomy.auth.entity.User;
import com.footonomy.common.entity.BaseEntity;
import com.footonomy.matches.entity.Team;
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
 * Real-FK replacement for one half of the data dictionary's polymorphic Following table —
 * see docs/07_Data_Dictionary.md "Following" section and V1__init_schema.sql header note.
 */
@Entity
@Table(name = "followed_teams", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "team_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowedTeam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
