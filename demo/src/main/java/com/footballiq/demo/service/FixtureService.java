package com.footballiq.demo.service;

import com.footballiq.demo.entity.MatchFixture;
import com.footballiq.demo.repository.MatchFixtureRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixtureService {

    private final MatchFixtureRepository matchFixtureRepository;

    public FixtureService(MatchFixtureRepository matchFixtureRepository) {
        this.matchFixtureRepository = matchFixtureRepository;
    }

    @Cacheable(value = "fixtures")
    public List<MatchFixture> getAllFixtures() {
        return matchFixtureRepository.findAll();
    }

    @Cacheable(value = "fixturesByLeague", key = "#league")
    public List<MatchFixture> getFixturesByLeague(String league) {
        return matchFixtureRepository.findByLeague(league);
    }

    @CacheEvict(value = {"fixtures", "fixturesByLeague"}, allEntries = true)
    public void evictAllCaches() {
        System.out.println("[Cache] Fixture caches evicted.");
    }
}
