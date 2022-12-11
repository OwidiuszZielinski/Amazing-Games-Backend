--liquibase formatted sql
--changeset owi:2

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
