create table if not exists candidate(
    id serial primary key,
    name text,
    description text,
    created timestamp,
    photo bytea,
    city_id int
);