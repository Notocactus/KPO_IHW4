CREATE TABLE users (
    id serial PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password INT NOT NULL,
    created TIMESTAMP DEFAULT now()
);
CREATE TABLE sessions (
    id serial PRIMARY KEY,
    userId INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires TIMESTAMP NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id)
);