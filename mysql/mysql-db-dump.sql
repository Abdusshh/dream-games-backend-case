DROP TABLE IF EXISTS team_members;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS teams;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  level INT DEFAULT 1,
  coins INT DEFAULT 5000
);

CREATE TABLE teams (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) UNIQUE,
  member_count INT DEFAULT 0,
  capacity INT DEFAULT 20
);

CREATE TABLE team_members (
    team_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (level, coins) VALUES (1, 5000);
INSERT INTO users (level, coins) VALUES (5, 5125);
INSERT INTO users (level, coins) VALUES (10, 5400);

INSERT INTO teams (name, member_count) VALUES ('Dream Chasers', 19);
INSERT INTO teams (name, member_count) VALUES ('Code Warriors', 5);

