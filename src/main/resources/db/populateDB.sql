DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories)
VALUES (1, 100001, '2015-06-01 14:00:00', 'Админ ланч', 510),
       (2, 100001, '2015-06-01 21:00:00', 'Админ ужин', 1500),
       (3, 100000, '2020-01-30 10:00:00', 'Завтрак', 500),
       (4, 100000, '2020-01-30 13:00:00', 'Обед', 1000),
       (5, 100000, '2020-01-30 20:00:00', 'Ужин', 500),
       (6, 100000, '2020-01-31 00:00:00', 'Еда на граничное значение', 100),
       (7, 100000, '2020-01-31 10:00:00', 'Завтрак', 500),
       (8, 100000, '2020-01-31 13:00:00', 'Обед', 1000),
       (9, 100000, '2020-01-31 20:00:00', 'Ужин', 500);
