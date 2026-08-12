package com.footballiq.demo.task;

import com.footballiq.demo.service.FixtureService;
import com.footballiq.demo.service.FootballScraperService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FootballSyncTask {

    private final FootballScraperService footballScraperService;
    private final FixtureService fixtureService;

    public FootballSyncTask(FootballScraperService footballScraperService, FixtureService fixtureService) {
        this.footballScraperService = footballScraperService;
        this.fixtureService = fixtureService;
    }

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void syncFixtures() {
        System.out.println("[SyncTask] Running scheduled fixture sync...");
        try {
            footballScraperService.scrapeAllLeagues();
            fixtureService.evictAllCaches();
            System.out.println("[SyncTask] Sync completed successfully.");
        } catch (Exception e) {
            System.err.println("[SyncTask] Sync failed: " + e.getMessage());
        }
    }
}
