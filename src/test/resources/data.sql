--liquibase formatted sql
--changeset owi:4
INSERT INTO game(id, availability, description, price, rating, title, "type")VALUES(1, '1', 'test', 0, 0, 'exampleOne', 'example');
INSERT INTO game(id, availability, description, price, rating, title, "type")VALUES(2, '1', 'test', 1, 1, 'exampleTwo', 'example');
INSERT INTO game(id, availability, description, price, rating, title, "type")VALUES(3, '1', 'test', 2, 2, 'exampleThree', 'example');
INSERT INTO game(id, availability, description, price, rating, title, "type")VALUES(4, '1', 'test', 3, 3, 'exampleFour', 'example');
INSERT INTO users
(id, uuid, address, country_id, email, "password", roles, username)
VALUES(1, '66b55ea6-0b8a-4110-ada9-56b9144c3dc7', 'example', 1, 'example@exampleOne.com','password','ROLE_USER', 'testUserOne');
INSERT INTO users
(id, uuid, address, country_id, email, "password", roles, username)
VALUES(2, '66b55ea6-0b8a-4110-ada9-56b9144c3dc6', 'example', 1, 'example@exampleTwo.com','password','ROLE_USER', 'testUserTwo');
INSERT INTO users
(id, uuid, address, country_id, email, "password", roles, username)
VALUES(3, '66b55ea6-0b8a-4110-ada9-56b9144c3dc5', 'example', 1, 'example@exampleThree.com','password','ROLE_USER', 'testUserThree');
INSERT INTO users
(id, uuid, address, country_id, email, "password", roles, username)
VALUES(4, '66b55ea6-0b8a-4110-ada9-56b9144c3dc4', 'example', 1, 'example@exampleFour.com','password','ROLE_USER', 'testUserFour');
INSERT INTO users
(id, uuid, address, country_id, email, "password", roles, username)
VALUES(5, '66b55ea6-0b8a-4110-ada9-56b9144c3dc2', 'example', 1, 'example@exampleFive.com','password','ROLE_USER', 'testUserFive');
INSERT INTO cart(id, user_id)VALUES(10, 1);
INSERT INTO cart(id, user_id)VALUES(11, 2);
INSERT INTO cart(id, user_id)VALUES(12, 3);
INSERT INTO cart(id, user_id)VALUES(13, 4);

INSERT INTO cart_detail(id, quantity, cart_id, game_id)VALUES(14, 1, 10, 1);
INSERT INTO cart_detail(id, quantity, cart_id, game_id)VALUES(15, 2, 11, 2);
INSERT INTO cart_detail(id, quantity, cart_id, game_id)VALUES(16, 3, 12, 3);
INSERT INTO cart_detail(id, quantity, cart_id, game_id)VALUES(17, 4, 13, 4);
