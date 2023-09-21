DROP TABLE IF EXISTS users;

CREATE TABLE users
(   id BIGINT GENERATED ALWAYS AS IDENTITY,
    email VARCHAR
);

INSERT INTO users (email) VALUES ('user1@gmail.com'),
                                 ('user2@gmail.com'),
                                 ('user3@gmail.com'),
                                 ('user4@gmail.com'),
                                 ('user5@gmail.com');