-- liquibase formatted sql

-- changeset SavelyDomnikov:1-insert-base-schema

INSERT INTO clients (full_name, date_birthday, gender)
VALUES ('Sean Andrews', '1983-12-09', 1),
       ('Sandra Berry', '2001-08-03', 2),
       ('Macon Sims', '1996-11-06', 1),
       ('Kylee Robbins', '1991-05-11', 2),
       ('Joshua Barr', '1991-05-07', 1);

INSERT INTO cars (model, color, engine_capacity, release_date, price)
VALUES ('bmw', 'black', 4.91, '2022-12-30', 5206623),
       ('toyota', 'blue', 2.82, '2018-02-24', 7217773),
       ('ford', 'white', 3.21, '2020-03-23', 2392601),
       ('tesla', 'red', 4.32, '2019-05-28', 7526001),
       ('nissan', 'black', 4.86, '2021-09-08', 1895657);

INSERT INTO orders (client_id, car_id, order_date)
VALUES (1, 5, '2021-11-01'),
       (2, 1, '2020-01-02'),
       (3, 2, '2021-01-21'),
       (4, 4, '2021-05-04'),
       (5, 1, '2022-01-05'),
       (2, 4, '2020-11-11'),
       (1, 3, '2022-05-07'),
       (3, 3, '2021-01-21'),
       (4, 5, '2020-04-26'),
       (1, 3, '2022-08-10')
