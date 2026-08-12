package com.footballiq.demo.repository;

import com.footballiq.demo.entity.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    List<MatchEvent> findByMatchIdOrderByMinuteAsc(String matchId);
    void deleteByMatchId(String matchId);
}
