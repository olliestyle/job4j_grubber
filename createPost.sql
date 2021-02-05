create table post(
    id serial primary key,
    name text,
    description text,
    link text NOT NULL unique,
    created timestamp NOT NULL
);