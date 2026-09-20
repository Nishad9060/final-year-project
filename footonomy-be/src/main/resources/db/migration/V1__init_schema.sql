-- Footonomy V1 initial schema.
-- Field names/types/nullability sourced from docs/07_Data_Dictionary.md (source of truth).
--
-- Deviation from the data dictionary's "Following" table: per the doc's own documented
-- known limitation and recommended fix, the polymorphic Following(entity_type, entity_id)
-- table is implemented here as two tables — followed_teams and followed_tournaments —
-- each with a real foreign key, instead of an app-layer-validated polymorphic reference.
-- docs/07_Data_Dictionary.md has been updated to reflect this.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE teams (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    short_name  VARCHAR(50),
    logo_url    VARCHAR(500),
    external_id VARCHAR(100) NOT NULL,
    CONSTRAINT uk_teams_external_id UNIQUE (external_id)
);

CREATE TABLE tournaments (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    season      VARCHAR(20) NOT NULL,
    external_id VARCHAR(100) NOT NULL,
    CONSTRAINT uk_tournaments_external_id UNIQUE (external_id)
);

CREATE TABLE matches (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tournament_id   UUID NOT NULL REFERENCES tournaments (id),
    home_team_id    UUID NOT NULL REFERENCES teams (id),
    away_team_id    UUID NOT NULL REFERENCES teams (id),
    kickoff_time    TIMESTAMP NOT NULL,
    status          VARCHAR(20) NOT NULL,
    home_score      INT,
    away_score      INT,
    last_synced_at  TIMESTAMP NOT NULL,
    CONSTRAINT ck_matches_status CHECK (status IN ('scheduled', 'live', 'finished'))
);

CREATE INDEX idx_matches_tournament_id ON matches (tournament_id);
CREATE INDEX idx_matches_kickoff_time ON matches (kickoff_time);

CREATE TABLE standings (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tournament_id UUID NOT NULL REFERENCES tournaments (id),
    team_id       UUID NOT NULL REFERENCES teams (id),
    position      INT NOT NULL,
    played        INT NOT NULL DEFAULT 0,
    won           INT NOT NULL DEFAULT 0,
    drawn         INT NOT NULL DEFAULT 0,
    lost          INT NOT NULL DEFAULT 0,
    points        INT NOT NULL DEFAULT 0,
    CONSTRAINT uk_standings_tournament_team UNIQUE (tournament_id, team_id)
);

CREATE INDEX idx_standings_tournament_id ON standings (tournament_id);

-- Split of the data dictionary's polymorphic "Following" table (see header note).
CREATE TABLE followed_teams (
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users (id),
    team_id UUID NOT NULL REFERENCES teams (id),
    CONSTRAINT uk_followed_teams_user_team UNIQUE (user_id, team_id)
);

CREATE INDEX idx_followed_teams_user_id ON followed_teams (user_id);

CREATE TABLE followed_tournaments (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id       UUID NOT NULL REFERENCES users (id),
    tournament_id UUID NOT NULL REFERENCES tournaments (id),
    CONSTRAINT uk_followed_tournaments_user_tournament UNIQUE (user_id, tournament_id)
);

CREATE INDEX idx_followed_tournaments_user_id ON followed_tournaments (user_id);

CREATE TABLE preferences (
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users (id),
    key     VARCHAR(100) NOT NULL,
    value   VARCHAR(255) NOT NULL,
    CONSTRAINT uk_preferences_user_key UNIQUE (user_id, key)
);

CREATE INDEX idx_preferences_user_id ON preferences (user_id);
