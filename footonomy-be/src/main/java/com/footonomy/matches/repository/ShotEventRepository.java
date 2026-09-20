package com.footonomy.matches.repository;

import com.footonomy.matches.entity.ShotEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShotEventRepository extends JpaRepository<ShotEvent, Long> {
    List<ShotEvent> findByMatchIdOrderByMinuteAsc(UUID matchId);
    void deleteByMatchId(UUID matchId);
}
