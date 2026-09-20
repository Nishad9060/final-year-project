package com.footonomy.matches.entity;

import com.footonomy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tournament extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String season;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;
}
