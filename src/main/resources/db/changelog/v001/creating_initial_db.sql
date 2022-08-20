--liquibase formatted sql

--changeset author:initial_db_creation

CREATE TABLE categories
(
    id bigserial primary key,
    title varchar(255)
);

CREATE TABLE products
(
    id          bigserial primary key,
    title       varchar(255),
    cost        int,
    category_id bigint references categories (id)
);

CREATE TABLE users
(
    id         bigserial primary key,
    username   varchar(30) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE roles
(
    id   bigserial primary key,
    name varchar(50) not null
);

CREATE TABLE privileges
(
    id   bigserial primary key,
    name varchar(50) not null
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

CREATE TABLE roles_privileges
(
    role_id      bigint not null references roles (id),
    privilege_id int    not null references privileges (id),
    primary key (privilege_id, role_id)
);

INSERT INTO categories (title)
values ('Food');

INSERT INTO products (title, cost, category_id)
values ('product1', 10, 1),
       ('product2', 100, 1),
       ('product3', 80, 1),
       ('product4', 25, 1),
       ('product5', 15, 1),
       ('product6', 66, 1),
       ('product7', 66, 1),
       ('product8', 900, 1),
       ('product9', 400, 1),
       ('product10', 2, 1),
       ('product11', 500, 1),
       ('product12', 550, 1),
       ('product13', 120, 1),
       ('product14', 190, 1),
       ('product15', 78, 1),
       ('product16', 35, 1),
       ('product17', 700, 1),
       ('product18', 680, 1),
       ('product19', 200, 1),
       ('product20', 190, 1);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into privileges (name)
values ('READ_PRIVILEGE'),
       ('WRITE_PRIVILEGE'),
       ('GOD_PRIVILEGE');

insert into users (username, password, email)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
       ('manager', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'manager@gmail.com'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into roles_privileges (role_id, privilege_id)
values (1, 1),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3);

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3);