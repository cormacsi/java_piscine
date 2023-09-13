DROP TABLE IF EXISTS "user", chatroom, message CASCADE;

CREATE TABLE "user"
(   id BIGINT GENERATED ALWAYS AS IDENTITY,
    login VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE chatroom
(   id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT pk_chatroom PRIMARY KEY (id),
    CONSTRAINT fk_chatroom_owner_id FOREIGN KEY (owner_id) REFERENCES "user"(id)
);

CREATE TABLE message
(   id BIGINT GENERATED ALWAYS AS IDENTITY,
    author_id BIGINT NOT NULL,
    chatroom_id BIGINT NOT NULL,
    text VARCHAR NOT NULL,
    date TIMESTAMP DEFAULT current_timestamp,
    CONSTRAINT pk_message PRIMARY KEY (id),
    CONSTRAINT fk_message_author_id FOREIGN KEY (author_id) REFERENCES "user"(id),
    CONSTRAINT fk_message_chatroom_id FOREIGN KEY (chatroom_id) REFERENCES chatroom(id)
);