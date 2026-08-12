package com.footballiq.demo.repository;

import com.footballiq.demo.entity.MatchFixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchFixtureRepository extends JpaRepository<MatchFixture, String> {
    List<MatchFixture> findByLeague(String league);
    List<MatchFixture> findByMatchStatus(String matchStatus);
}
