package com.footballiq.demo.repository;

import com.footballiq.demo.entity.ShotEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShotEventRepository extends JpaRepository<ShotEvent, Long> {
    List<ShotEvent> findByMatchIdOrderByMinuteAsc(String matchId);
    void deleteByMatchId(String matchId);
}
