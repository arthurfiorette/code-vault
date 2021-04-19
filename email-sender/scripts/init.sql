create database emails_database;

\c emails_database

create table emails (
    id serial not null,
    data timestamp not null default current_timestamp,
    title varchar(100) not null,
    content varchar(250) not null
);