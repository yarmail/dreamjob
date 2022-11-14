create table if not exists users(
    id serial primary key,
    name varchar,
    password text,
    email varchar unique
);