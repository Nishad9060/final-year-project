package com.footonomy.matches.service.footballdata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Polls football-data.org on a fixed delay (default 7 min, see application.yml) and refreshes
 * local cache. A failure for one competition — including the API being rate-limited or down —
 * is caught and logged here so the rest of the competitions in this cycle still sync, and so the
 * next cycle simply retries; controllers keep serving whatever was last synced (docs/03_TRD.md §5).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FootballDataSyncJob {

    private final FootballDataSyncService syncService;

    @Value("${football-data.sync.enabled:false}")
    private boolean enabled;

    @Scheduled(fixedDelayString = "${football-data.sync.fixed-delay-ms:420000}")
    public void syncAll() {
        if (!enabled) {
            return;
        }
        for (String code : SupportedCompetitions.CODES) {
            try {
                syncService.syncCompetition(code);
            } catch (Exception e) {
                log.warn("football-data.org sync failed for competition {} — serving last-cached data. Cause: {}",
                        code, e.getMessage());
            }
        }
    }
}
