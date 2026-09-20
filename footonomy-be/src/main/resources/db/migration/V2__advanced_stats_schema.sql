-- Advanced Match Details (1-to-1 with matches table)
CREATE TABLE match_details (
    id UUID PRIMARY KEY REFERENCES matches(id),
    possession_home INT,
    possession_away INT,
    shots_home INT,
    shots_away INT,
    shots_on_target_home INT,
    shots_on_target_away INT,
    fouls_home INT,
    fouls_away INT,
    corners_home INT,
    corners_away INT,
    yellow_cards_home INT,
    yellow_cards_away INT,
    red_cards_home INT,
    red_cards_away INT,
    home_formation VARCHAR(50),
    away_formation VARCHAR(50),
    home_starting_xi VARCHAR(1000),
    away_starting_xi VARCHAR(1000),
    expected_goals_home DECIMAL(5,2),
    expected_goals_away DECIMAL(5,2),
    last_updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Match Timeline Events
CREATE TABLE match_events (
    id BIGSERIAL PRIMARY KEY,
    match_id UUID NOT NULL REFERENCES matches(id),
    minute INT NOT NULL,
    team VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    player VARCHAR(255),
    detail VARCHAR(500)
);
CREATE INDEX idx_match_events_match_id ON match_events(match_id);

-- Advanced Shotmap Events (Understat)
CREATE TABLE shot_events (
    id BIGSERIAL PRIMARY KEY,
    match_id UUID NOT NULL REFERENCES matches(id),
    minute INT NOT NULL,
    team VARCHAR(255) NOT NULL,
    player VARCHAR(255),
    x DECIMAL(5,3),
    y DECIMAL(5,3),
    xg DECIMAL(5,3),
    result VARCHAR(100),
    situation VARCHAR(100),
    shot_type VARCHAR(100)
);
CREATE INDEX idx_shot_events_match_id ON shot_events(match_id);
