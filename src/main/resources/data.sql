insert into USER_INFO (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER, POINTS)
values (1, 'Adam', 'Sz', 'adam@test.com','a1', '09893434', 0);
insert into USER_INFO (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,  PHONE_NUMBER, POINTS)
values (2, 'John', 'Ad', 'john@test.com','a1', '05678', 1200);
insert into USER_INFO (ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,  PHONE_NUMBER, POINTS)
values (3, 'Test', 'T', 'test@test.com','a1', '01', 200);


insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (1, 2, 200, 'Basic');
insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (2, 2, 200, 'Basic');
insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (3, 2, 200, 'Basic');
insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (4, 4, 400, 'Standard');
insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (5, 6, 800, 'Premium');
insert into ROOM (ID, MAX_NUMBER_OF_GUESTS, PRICE_PER_NIGHT, ROOM_CATEGORY)
values (6, 8, 1000, 'Premium');

--insert into RESERVATION( ID, ARRIVAL, DEPARTURE, TOTAL_PRICE, USERNAME, GUESTS, ROOM_CLASS, USER_ID)
--VALUES (1, '2023-06-01', '2023-06-04', 800, 'adam@test.com', 2, 'Basic', 1);
--insert into RESERVATION( ID, ARRIVAL, DEPARTURE, TOTAL_PRICE, USERNAME,  GUESTS, ROOM_CLASS, USER_ID)
--VALUES (2, '2023-08-01', '2023-08-10', 2000, 'adam@test.com',2, 'Basic',  1);