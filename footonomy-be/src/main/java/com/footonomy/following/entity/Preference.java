package com.footonomy.following.entity;

import com.footonomy.auth.entity.User;
import com.footonomy.common.entity.BaseEntity;
import jakarta.persistence.Column;
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

@Entity
@Table(name = "preferences", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "key"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preference extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // "key" is a reserved word in H2's default keyword set (though not in Postgres) — the test
    // datasource URL sets NON_KEYWORDS=KEY,VALUE so this can stay unquoted, matching the Data
    // Dictionary's column name exactly.
    @Column(name = "key", nullable = false, length = 100)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;
}
