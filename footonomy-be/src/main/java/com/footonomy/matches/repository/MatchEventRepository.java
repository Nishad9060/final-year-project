package com.footonomy.matches.repository;

import com.footonomy.matches.entity.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    List<MatchEvent> findByMatchIdOrderByMinuteAsc(UUID matchId);
    void deleteByMatchId(UUID matchId);
}
