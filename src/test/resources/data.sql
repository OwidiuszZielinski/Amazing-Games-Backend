CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE game (
  id INTEGER NOT NULL,
   title VARCHAR(255),
   type VARCHAR(255),
   price DOUBLE PRECISION NOT NULL,
   description VARCHAR(500),
   rating DOUBLE PRECISION NOT NULL,
   availability BOOLEAN NOT NULL,
   CONSTRAINT pk_game PRIMARY KEY (id)
);
CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
  id INTEGER NOT NULL,
   uuid VARCHAR(255),
   username VARCHAR(255),
   password VARCHAR(255),
   email VARCHAR(255),
   country_id INTEGER NOT NULL,
   address VARCHAR(255),
   roles VARCHAR(255),
   CONSTRAINT pk_users PRIMARY KEY (id)
);
CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE cart (
  id INTEGER NOT NULL,
   user_id INTEGER,
   CONSTRAINT pk_cart PRIMARY KEY (id)
);

ALTER TABLE cart ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE cart_detail (
  id INTEGER NOT NULL,
   game_id INTEGER,
   cart_id INTEGER,
   quantity INTEGER NOT NULL,
   CONSTRAINT pk_cartdetail PRIMARY KEY (id)
);

ALTER TABLE cart_detail ADD CONSTRAINT FK_CARTDETAIL_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_detail ADD CONSTRAINT FK_CARTDETAIL_ON_GAME FOREIGN KEY (game_id) REFERENCES game (id);


insert into users
(id, uuid, address, country_id, email, "password", roles, username)
values(1, '66b55ea6-0b8a-4110-ada9-56b9144c3dc7', 'example', 1, 'example@exampleOne.com','password','ROLE_USER', 'testUserOne');
insert into users
(id, uuid, address, country_id, email, "password", roles, username)
values(2, '66b55ea6-0b8a-4110-ada9-56b9144c3dc6', 'example', 1, 'example@exampleTwo.com','password','ROLE_USER', 'testUserTwo');
insert into users
(id, uuid, address, country_id, email, "password", roles, username)
values(3, '66b55ea6-0b8a-4110-ada9-56b9144c3dc5', 'example', 1, 'example@exampleThree.com','password','ROLE_USER', 'testUserThree');
insert into users
(id, uuid, address, country_id, email, "password", roles, username)
values(4, '66b55ea6-0b8a-4110-ada9-56b9144c3dc4', 'example', 1, 'example@exampleFour.com','password','ROLE_USER', 'testUserFour');
insert into users
(id, uuid, address, country_id, email, "password", roles, username)
values(5, '66b55ea6-0b8a-4110-ada9-56b9144c3dc2', 'example', 1, 'example@exampleFive.com','password','ROLE_USER', 'testUserFive');
insert into game(id, availability, description, price, rating, title, "type")values(1, '1', 'test', 0, 0, 'exampleOne', 'example');
insert into game(id, availability, description, price, rating, title, "type")values(2, '1', 'test', 1, 1, 'exampleTwo', 'example');
insert into game(id, availability, description, price, rating, title, "type")values(3, '1', 'test', 2, 2, 'exampleThree', 'example');
insert into game(id, availability, description, price, rating, title, "type")values(4, '1', 'test', 3, 3, 'exampleFour', 'example');
insert into cart(id, user_id)values(1, 1);
insert into cart(id, user_id)values(2, 2);
insert into cart(id, user_id)values(3, 3);
insert into cart(id, user_id)values(4, 4);
insert into cart_detail(id, quantity, cart_id, game_id)values(1, 1, 1, 1);
insert into cart_detail(id, quantity, cart_id, game_id)values(2, 2, 2, 2);
insert into cart_detail(id, quantity, cart_id, game_id)values(3, 3, 3, 3);
insert into cart_detail(id, quantity, cart_id, game_id)values(4, 4, 4, 4);
