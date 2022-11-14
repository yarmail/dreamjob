create table if not exists Users(
    id serial primary key,
    name varchar (100),
    password text,
    email varchar(100) unique
);