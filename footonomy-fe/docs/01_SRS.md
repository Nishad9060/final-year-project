# Software Requirements Specification (SRS) — Footonomy V1

**Version:** 1.0 | **Target delivery:** Aug 14, 2026 | **Scope:** Web App + Backend only

## 1. Purpose & Scope
Footonomy V1 is a football companion web app covering live-ish scores, match stats, tournaments/standings, personalized following, and search — scoped to the top 5 European leagues + Champions League. Games and Content Pipeline are **explicitly out of scope** for this document set; they are functionally near-complete per prior status and will be specified separately.

## 2. Out of Scope (V1)
- Games module (any form — predictions, fantasy, trivia)
- Content Pipeline (auto-generated content, news pipeline)
- Mobile app (native Android/iOS)
- Leagues outside top 5 + Champions League
- Player-level stats requiring paid API tier (lineups, substitutions, cards)
- Real-time (sub-minute) live scores — see Constraint C-1

## 3. Users
- **Guest user**: browses matches, scores, tournaments, standings, search — no account required
- **Registered user**: additionally can follow teams/tournaments, personalize a feed, manage settings

## 4. Functional Requirements

### 4.1 Matches
- FR-1: Display today's matches grouped by league, with score/status (upcoming/live/finished)
- FR-2: Filter matches by date and league
- FR-3: View match detail — score, basic timeline/events (if available from data source), tournament context
- FR-4: Guest-accessible, no auth required

### 4.2 Tournaments / Standings
- FR-5: List supported tournaments (top 5 leagues + Champions League)
- FR-6: View tournament standings table
- FR-7: View tournament fixture list
- FR-8: Guest-accessible, no auth required

### 4.3 Search
- FR-9: Search across teams, tournaments, and players
- FR-10: Results grouped by entity type
- FR-11: Guest-accessible, no auth required
- Note: player search depends on data source coverage — confirm player entity availability in chosen API tier before committing full implementation

### 4.4 Auth
- FR-12: Registered users can sign up and log in via email + password
- FR-13: Auth required only to access Following and personalized Settings — not required for Matches, Tournaments, or Search
- FR-14: Session persistence via token (JWT) after login

### 4.5 Following
- FR-15: Authenticated users can follow/unfollow teams and tournaments
- FR-16: Authenticated users see a personalized feed of followed entities' matches
- FR-17: Empty state shown when no follows exist yet
- FR-18: Requires auth (FR-13)

### 4.6 Settings
- FR-19: Authenticated users can view/edit profile info
- FR-20: Authenticated users can manage notification/display preferences (scope of actual notification delivery TBD — may be UI-only for V1)

## 5. Non-Functional Requirements
- NFR-1: Web app must be responsive (desktop, tablet, mobile web) — no native mobile app in V1
- NFR-2: Backend must respect third-party API rate limits via caching (see TRD §5)
- NFR-3: Auth tokens must not be stored in plaintext or exposed client-side beyond standard JWT handling
- NFR-4: Guest-accessible pages must load without requiring any auth check round-trip

## 6. Constraints
- **C-1 (Data freshness)**: Chosen data source (football-data.org free tier) provides delayed, not real-time, scores. "Live scores" in this product means near-live, refreshed on a polling interval — not sub-second updates. This must be communicated in UI copy, not silently implied.
- **C-2 (Rate limits)**: 10 requests/minute on the data source free tier. All match/tournament data must be cached server-side; no direct client-to-third-party-API calls.
- **C-3 (Timeline)**: 6 days from spec to delivery (Aug 8–14, 2026) across a 3-person team. Any scope addition beyond this document requires an explicit cut elsewhere.
- **C-4 (Team skill)**: Spring Boot chosen as backend framework; confirm team familiarity before build starts, as unfamiliarity adds real risk to an already tight timeline.

## 7. Open Items Requiring Decision Before Build
- [ ] Confirm top 5 leagues + CL is the final V1 league set
- [ ] Confirm player search is in scope given data source player-data limitations
- [ ] Confirm who owns the API contract document and has authority to freeze/change it
- [ ] Confirm settings/notification scope — UI-only vs. functional
