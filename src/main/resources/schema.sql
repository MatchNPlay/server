-- USERS table: stores user profiles
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    skill_level VARCHAR(20),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- GAMES table: stores games created by hosts
CREATE TABLE IF NOT EXISTS games (
    id SERIAL PRIMARY KEY,
    host_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    sport VARCHAR(50) NOT NULL,
    title VARCHAR(100),
    description TEXT,
    required_players INT NOT NULL,
    required_positions TEXT, -- comma separated positions or JSON string
    skill_level VARCHAR(20),
    start_date DATE,  -- scheduled start date
    end_date DATE,    -- scheduled end date
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_scheduled BOOLEAN GENERATED ALWAYS AS
        (start_date IS NOT NULL AND end_date IS NOT NULL) STORED
);

-- TEAMS table: teams belonging to a game
CREATE TABLE IF NOT EXISTS teams (
    id SERIAL PRIMARY KEY,
    game_id INT NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    name VARCHAR(100),
    is_opposite_team BOOLEAN DEFAULT FALSE,
    max_players INT NOT NULL DEFAULT 5,
    status VARCHAR(20) DEFAULT 'waiting',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- GAME_PLAYERS table: players linked to teams
CREATE TABLE IF NOT EXISTS game_players (
    id SERIAL PRIMARY KEY,
    team_id INT NOT NULL REFERENCES teams(id) ON DELETE CASCADE,
    player_id INT REFERENCES users(id) ON DELETE SET NULL,
    position VARCHAR(50),
    status VARCHAR(20) DEFAULT 'pending', -- pending, confirmed, rejected
    added_by_host BOOLEAN DEFAULT FALSE,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- FEEDBACK table: stores feedback as player and as host
CREATE TABLE IF NOT EXISTS feedback (
    id SERIAL PRIMARY KEY,
    game_id INT NOT NULL REFERENCES games(id) ON DELETE CASCADE,
    from_user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    to_user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(10) NOT NULL, -- 'host' or 'player'
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- GROUPS table: discussion groups, can be public or private
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_private BOOLEAN DEFAULT FALSE,
    owner_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- CHAT_MESSAGES table: individual chat messages between users
CREATE TABLE IF NOT EXISTS chat_messages (
    id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    -- Either receiver_id for personal chat OR group_id for group chat (only one of these non-null)
    receiver_id INT REFERENCES users(id) ON DELETE CASCADE,
    group_id INT REFERENCES groups(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (
      (receiver_id IS NOT NULL AND group_id IS NULL) OR
      (receiver_id IS NULL AND group_id IS NOT NULL)
    )
);

-- GROUP_MEMBERS table: users who joined groups, with status for private groups
CREATE TABLE IF NOT EXISTS group_members (
    id SERIAL PRIMARY KEY,
    group_id INT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(20) DEFAULT 'pending', -- pending, approved, rejected
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- GROUP_CHAT_MESSAGES table: messages sent inside groups
CREATE TABLE IF NOT EXISTS group_chat_messages (
    id SERIAL PRIMARY KEY,
    group_id INT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    sender_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);