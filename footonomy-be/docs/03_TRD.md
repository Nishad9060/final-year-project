# Technical Requirements Document (TRD) — Footonomy V1

## 1. Architecture Overview

```
[React Web App] ──HTTPS──> [Spring Boot Backend API] ──> [PostgreSQL]
                                     │
                                     └──cached calls──> [football-data.org API]
```

- Frontend: React (web only, responsive)
- Backend: Spring Boot (Java), REST API
- Database: PostgreSQL
- External data: football-data.org (free tier)
- Hosting: Frontend on Vercel; Backend + Postgres on Railway or Render (confirm one before build)

## 2. Module Ownership (per prior team division)

| Person | Backend | Frontend |
|---|---|---|
| A | Data ingestion, Match/Tournament API | Matches, Match Detail, Tournaments, Standings |
| B | Auth service, User/Preferences storage | Auth screens, Settings |
| C | Following service, Search service | Following, Search |

**Contract owner (assign explicitly before build starts):** ______________. This person has authority to freeze the API contract after initial agreement and must approve any breaking change.

## 3. Data Model (core entities)

```
User
- id, email, password_hash, created_at

Team
- id, name, short_name, logo_url, external_id (football-data.org ref)

Tournament
- id, name, season, external_id

Match
- id, tournament_id, home_team_id, away_team_id, kickoff_time,
  status (scheduled/live/finished), home_score, away_score,
  last_synced_at

Standing
- id, tournament_id, team_id, position, played, won, drawn, lost, points

Following
- id, user_id, entity_type (team/tournament), entity_id

Preference
- id, user_id, key, value
```

## 4. API Contract (V1 endpoints)

**Public (no auth):**
- `GET /api/matches?date=&league=`
- `GET /api/matches/:id`
- `GET /api/tournaments`
- `GET /api/tournaments/:id`
- `GET /api/tournaments/:id/standings`
- `GET /api/tournaments/:id/fixtures`
- `GET /api/search?q=&type=`

**Auth:**
- `POST /api/auth/signup`
- `POST /api/auth/login`
- `POST /api/auth/logout`

**Authenticated (requires valid JWT):**
- `GET /api/users/me`
- `PUT /api/users/me`
- `GET /api/users/me/preferences`
- `PUT /api/users/me/preferences`
- `GET /api/users/me/following`
- `POST /api/users/me/following`
- `DELETE /api/users/me/following/:id`

This contract must be frozen after initial team agreement (target: Aug 9). Changes after that point require sync between the contract owner and whichever of A/B/C is affected.

## 5. Third-Party Data Integration Strategy
- **Source**: football-data.org free tier — 12 competitions incl. top 5 leagues + Champions League, 10 req/min, delayed scores, no player/lineup data on free tier
- **Caching**: All match/tournament/standings data cached server-side in Postgres. Scheduled sync job polls football-data.org on an interval that respects the 10 req/min cap (recommend: sync fixtures/standings every 5–10 min, not per-request)
- **No direct client → third-party API calls**, ever — this both protects the rate limit and lets the backend normalize the schema (don't leak football-data.org's field names into the frontend contract)
- **Failure handling**: if sync fails or rate limit is hit, serve last-cached data with a `last_synced_at` timestamp the frontend can use to show "data may be delayed" — do not show a blank/broken state

## 6. Auth Implementation
- Email + password, JWT-based session
- Public endpoints require zero auth overhead — do not gate matches/tournaments/search behind any auth middleware
- Auth-required endpoints validated via JWT middleware; expired/invalid token → 401, frontend redirects to `/login`

## 7. Deployment
- Frontend: Vercel (React build)
- Backend: Railway or Render (Java/Spring Boot support) + managed Postgres add-on — confirm which platform before Aug 9, don't decide mid-build
- Environment variables (API keys, DB connection, JWT secret) never committed to repo — use platform secret management

## 7a. Build Tooling (added — confirm with team before build starts)
- **Backend build tool**: Maven (over Gradle — simpler, more universally represented in agent training data, reduces risk of inconsistent build config across independent agent sessions)
- **DB migrations**: Flyway — SQL-based, versioned migrations required given multiple people modifying schema in parallel via agents. Every schema change must be a new migration file, never a manual DB edit.
- **Frontend build tool**: Vite (not Create React App — CRA is deprecated) — React + Vite
- **Frontend state management**: React Context + hooks for V1 scope (auth state, following state). Do not introduce Redux/Zustand/etc. unless a specific need emerges — added complexity not justified by V1 scope or timeline.
- **Frontend HTTP client**: a single shared API client module (axios or fetch wrapper) matching endpoints in §4 exactly — no ad-hoc fetch calls scattered across components.

## 8. Explicit Non-Goals for V1 (stated so agentic coding tools don't scope-creep)
- No real-time WebSocket score updates — polling only
- No player-level stats (lineups, cards, substitutions) — free tier doesn't provide them
- No native mobile build
- No Games or Content Pipeline integration
