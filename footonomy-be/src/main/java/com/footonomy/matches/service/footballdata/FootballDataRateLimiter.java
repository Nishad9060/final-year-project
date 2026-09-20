package com.footonomy.matches.service.footballdata;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import org.springframework.stereotype.Component;

/**
 * Sliding-window limiter enforcing football-data.org's free-tier cap of 10 requests/minute
 * (docs/03_TRD.md §5, docs/01_SRS.md C-2). Blocks the calling (scheduler) thread rather than
 * throwing, since a sync cycle simply needs to slow down, not fail.
 */
@Component
public class FootballDataRateLimiter {

    private static final int MAX_REQUESTS_PER_WINDOW = 10;
    private static final Duration WINDOW = Duration.ofSeconds(60);

    private final Deque<Instant> requestTimestamps = new ArrayDeque<>();

    public synchronized void acquire() {
        while (true) {
            Instant now = Instant.now();
            while (!requestTimestamps.isEmpty()
                    && Duration.between(requestTimestamps.peekFirst(), now).compareTo(WINDOW) >= 0) {
                requestTimestamps.pollFirst();
            }

            if (requestTimestamps.size() < MAX_REQUESTS_PER_WINDOW) {
                requestTimestamps.addLast(now);
                return;
            }

            Duration waitFor = WINDOW.minus(Duration.between(requestTimestamps.peekFirst(), now));
            sleep(waitFor);
        }
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(Math.max(0, duration.toMillis()) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while rate-limiting football-data.org calls", e);
        }
    }
}
