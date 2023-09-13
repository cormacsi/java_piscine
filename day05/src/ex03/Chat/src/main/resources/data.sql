TRUNCATE TABLE "user", chatroom, message;

INSERT INTO "user" (login, password) VALUES ('Mike', 'password');
INSERT INTO "user" (login, password) VALUES ('Regina', 'password');
INSERT INTO "user" (login, password) VALUES ('Kile', 'password');
INSERT INTO "user" (login, password) VALUES ('Maeve', 'password');
INSERT INTO "user" (login, password) VALUES ('Otis', 'password');
INSERT INTO "user" (login, password) VALUES ('Peter', 'password');
INSERT INTO "user" (login, password) VALUES ('user', 'user');

INSERT INTO chatroom (name, owner_id) VALUES ('School', 1);
INSERT INTO chatroom (name, owner_id) VALUES ('Private chat', 1);
INSERT INTO chatroom (name, owner_id) VALUES ('Heroes', 2);
INSERT INTO chatroom (name, owner_id) VALUES ('Friends', 3);
INSERT INTO chatroom (name, owner_id) VALUES ('Team', 4);
INSERT INTO chatroom (name, owner_id) VALUES ('Work', 5);
INSERT INTO chatroom (name, owner_id) VALUES ('Girls', 2);
INSERT INTO chatroom (name, owner_id) VALUES ('room', 7);

INSERT INTO message (author_id, chatroom_id, text) VALUES (1, 1, 'Hi');
INSERT INTO message (author_id, chatroom_id, text) VALUES (1, 1, 'Hi');
INSERT INTO message (author_id, chatroom_id, text) VALUES (4, 1, 'Hey!');
INSERT INTO message (author_id, chatroom_id, text) VALUES (5, 1, 'Hello');
INSERT INTO message (author_id, chatroom_id, text) VALUES (1, 2, 'Private chat is here!');
INSERT INTO message (author_id, chatroom_id, text) VALUES (2, 2, 'Nice');
INSERT INTO message (author_id, chatroom_id, text) VALUES (1, 3, 'Hello friends');
INSERT INTO message (author_id, chatroom_id, text) VALUES (2, 3, 'Hello friends');
INSERT INTO message (author_id, chatroom_id, text) VALUES (3, 3, 'Hello friends');
INSERT INTO message (author_id, chatroom_id, text) VALUES (2, 4, 'Hi team');
INSERT INTO message (author_id, chatroom_id, text) VALUES (4, 4, 'Hi team');
INSERT INTO message (author_id, chatroom_id, text) VALUES (5, 4, 'Hi team');
INSERT INTO message (author_id, chatroom_id, text) VALUES (5, 5, 'Work chat');