insert into USER_INFO (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER, DISCOUNT_LEVEL, POINTS)
values ( 'Adam', 'Sz', 'adam@test.com','$2a$10$J7eFD6Z4mX1NGPeku1467uWlFzx0JjdBB.oBplzVF1pTck5N75jA2', '09893434','0%', 0);
insert into USER_INFO (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,  PHONE_NUMBER, DISCOUNT_LEVEL, POINTS)
values ('John', 'Ad', 'john@test.com','$2a$10$Vy1P7f5epa3wOZHofeADYe2/fkpd.8HiIyyTy6nL5xTqnsgkM29ly', '05678','0%', 1200);
insert into USER_INFO ( FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,  PHONE_NUMBER, DISCOUNT_LEVEL, POINTS)
values ( 'Test', 'T', 'test@test.com','$2a$10$RLhewL4cFkfyzrlY6M3n.OHd8HdzURC8VjdnAh7hlKmZVV5gQDyo.', '01','0%', 200);

insert into ROLE(NAME)
values('CUSTOMER');
insert into ROLE(NAME)
values('EMPLOYEE');
insert into ROLE(NAME)
values('MANAGER');

INSERT INTO user_roles (id, rid) VALUES (1, 3);
INSERT INTO user_roles (id, rid) VALUES (2, 2);
INSERT INTO user_roles (id, rid) VALUES (3, 1 );



insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (2, 200, 'Basic');
insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (2, 200, 'Basic');
insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (2, 200, 'Basic');
insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (4, 400, 'Standard');
insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (6, 800, 'Premium');
insert into ROOM (MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (8, 1000, 'Premium');

insert into RESERVATION(ARRIVAL, DEPARTURE, TOTAL_PRICE, USERNAME, GUESTS, ROOM_CLASS, USER_ID)
VALUES ('2023-06-01', '2023-06-04', 800, 'adam@test.com', 2, 'Basic', 1);
insert into RESERVATION(ARRIVAL, DEPARTURE, TOTAL_PRICE, USERNAME,  GUESTS, ROOM_CLASS, USER_ID)
VALUES ('2023-08-01', '2023-08-10', 2000, 'adam@test.com',2, 'Basic',  1);