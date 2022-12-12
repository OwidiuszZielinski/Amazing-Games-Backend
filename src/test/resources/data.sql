insert into users(uuid, address, country_id, email, "password", roles, username)
values('66b55ea6-0b8a-4110-ada9-56b9144c3dc7', 'example', 1, 'example@exampleOne.com','password','ROLE_USER', 'testUserOne');
insert into users
( uuid, address, country_id, email, "password", roles, username)
values( '66b55ea6-0b8a-4110-ada9-56b9144c3dc6', 'example', 1, 'example@exampleTwo.com','password','ROLE_USER', 'testUserTwo');
insert into users
( uuid, address, country_id, email, "password", roles, username)
values( '66b55ea6-0b8a-4110-ada9-56b9144c3dc5', 'example', 1, 'example@exampleThree.com','password','ROLE_USER', 'testUserThree');
insert into users
(uuid, address, country_id, email, "password", roles, username)
values('66b55ea6-0b8a-4110-ada9-56b9144c3dc4', 'example', 1, 'example@exampleFour.com','password','ROLE_USER', 'testUserFour');
insert into users
(uuid, address, country_id, email, "password", roles, username)
values('66b55ea6-0b8a-4110-ada9-56b9144c3dc2', 'example', 1, 'example@exampleFive.com','password','ROLE_USER', 'testUserFive');
insert into game(availability, description, price, rating, title, "type")values('1', 'test', 0, 0, 'exampleOne', 'example');
insert into game(availability, description, price, rating, title, "type")values( '1', 'test', 1, 1, 'exampleTwo', 'example');
insert into game(availability, description, price, rating, title, "type")values('1', 'test', 2, 2, 'exampleThree', 'example');
insert into game(availability, description, price, rating, title, "type")values('1', 'test', 3, 3, 'exampleFour', 'example');
insert into orders("date", status, value, user_id)values('', 1, 1, 1);
insert into orders("date", status, value, user_id)values('', 1, 1, 2);
insert into orders("date", status, value, user_id)values('', 1, 1, 3);
insert into orders( "date", status, value, user_id)values( '', 1, 1, 4);
insert into cart(user_id)values( 1);
insert into cart( user_id)values( 2);
insert into cart(user_id)values(3);
insert into cart( user_id)values(4);
insert into cart_detail( quantity, cart_id, game_id)values( 1, 1, 1);
insert into cart_detail( quantity, cart_id, game_id)values( 2, 2, 2);
insert into cart_detail( quantity, cart_id, game_id)values( 3, 3, 3);
insert into cart_detail(quantity, cart_id, game_id)values( 4, 4, 4);
