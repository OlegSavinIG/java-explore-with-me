-- Schema for table: compilations
CREATE TABLE IF NOT EXISTS compilations (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    pinned BOOLEAN
);

-- Schema for table: events
CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    annotation TEXT,
    description TEXT,
    created_on TIMESTAMP NOT NULL,
    event_date TIMESTAMP NOT NULL,
    published_on TIMESTAMP,
    paid BOOLEAN NOT NULL,
    views INTEGER,
    confirmed_requests INTEGER,
    participant_limit INTEGER,
    request_moderation BOOLEAN,
    state VARCHAR(255),
    category_id INTEGER,
    user_id INTEGER,
    CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);

-- Schema for table: subscriptions
CREATE TABLE IF NOT EXISTS subscriptions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    subscriber_id INTEGER,
    status VARCHAR(255),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_subscriber FOREIGN KEY(subscriber_id) REFERENCES users(id)
);

-- Schema for table: users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Schema for table: user_friends (join table for users' friends)
CREATE TABLE IF NOT EXISTS user_friends (
    user_id INTEGER,
    friend_id INTEGER,
    PRIMARY KEY(user_id, friend_id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_friend FOREIGN KEY(friend_id) REFERENCES users(id)
);

-- Schema for table: requests
CREATE TABLE IF NOT EXISTS requests (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    event_id INTEGER,
    user_id INTEGER,
    status VARCHAR(255),
    CONSTRAINT fk_event FOREIGN KEY(event_id) REFERENCES events(id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);

-- Schema for join table: compilation_events
CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id INTEGER,
    event_id INTEGER,
    PRIMARY KEY(compilation_id, event_id),
    CONSTRAINT fk_compilation FOREIGN KEY(compilation_id) REFERENCES compilations(id),
    CONSTRAINT fk_event FOREIGN KEY(event_id) REFERENCES events(id)
);

-- Schema for table: categories
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
