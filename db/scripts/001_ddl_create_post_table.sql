create table Post (
    id serial primary key,
    name text,
    description text,
    created timestamp,
    visible boolean,
    city_id int
);