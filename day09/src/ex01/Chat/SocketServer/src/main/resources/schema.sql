DROP TABLE IF EXISTS users, messages;

CREATE TABLE users
(   id       BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR,
    password VARCHAR,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE messages
(   id        BIGINT GENERATED ALWAYS AS IDENTITY,
    text      VARCHAR,
    time      TIMESTAMP DEFAULT current_timestamp,
    sender_id BIGINT NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id),
    CONSTRAINT fk_messages_sender_id FOREIGN KEY (sender_id) REFERENCES users(id)
);