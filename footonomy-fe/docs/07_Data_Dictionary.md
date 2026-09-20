# Data Dictionary — Footonomy V1

Companion to `06_ER_Diagram.mermaid` and `03_TRD.md` §3. This is the source of truth for field names/types — agents should not invent field names not listed here.

## User

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| email | VARCHAR(255) | No | UNIQUE | |
| password_hash | VARCHAR(255) | No | | Never store plaintext; bcrypt or equivalent |
| created_at | TIMESTAMP | No | default now() | |

## Team

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| name | VARCHAR(255) | No | | Full team name |
| short_name | VARCHAR(50) | Yes | | For compact UI (match cards) |
| logo_url | VARCHAR(500) | Yes | | |
| external_id | VARCHAR(100) | No | UNIQUE | football-data.org team ID, used for sync matching |

## Tournament

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| name | VARCHAR(255) | No | | e.g. "Premier League" |
| season | VARCHAR(20) | No | | e.g. "2025/26" |
| external_id | VARCHAR(100) | No | UNIQUE | football-data.org competition ID |

## Match

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| tournament_id | UUID | No | FK → Tournament.id | |
| home_team_id | UUID | No | FK → Team.id | |
| away_team_id | UUID | No | FK → Team.id | |
| kickoff_time | TIMESTAMP | No | | Store in UTC, convert client-side |
| status | VARCHAR(20) | No | ENUM: scheduled, live, finished | |
| home_score | INT | Yes | | Null until match starts |
| away_score | INT | Yes | | Null until match starts |
| last_synced_at | TIMESTAMP | No | | Used to show data-staleness indicator per TRD §5 |

## Standing

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| tournament_id | UUID | No | FK → Tournament.id | |
| team_id | UUID | No | FK → Team.id | |
| position | INT | No | | |
| played | INT | No | default 0 | |
| won | INT | No | default 0 | |
| drawn | INT | No | default 0 | |
| lost | INT | No | default 0 | |
| points | INT | No | default 0 | |

## Following
**Known limitation (see flag above):** `entity_id` is polymorphic, not a true FK. Recommended fix: split into `FollowedTeam` and `FollowedTournament` tables with real FKs. Table below documents the current (as-specified) polymorphic version; update if the team decides to split it.

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| user_id | UUID | No | FK → User.id | |
| entity_type | VARCHAR(20) | No | ENUM: team, tournament | |
| entity_id | UUID | No | NOT a true FK — app-layer validation required | |

## Preference

| Field | Type | Nullable | Constraints | Notes |
|---|---|---|---|---|
| id | UUID | No | PK | |
| user_id | UUID | No | FK → User.id | |
| key | VARCHAR(100) | No | | e.g. "notify_followed_matches" |
| value | VARCHAR(255) | No | | Stored as string; parse per key at app layer |

## Naming Conventions
- snake_case for all DB columns
- UUID for all primary keys (not auto-increment int) — avoids exposing sequential IDs in public API responses
- Timestamps in UTC, `TIMESTAMP` type, converted to local time only at the frontend
