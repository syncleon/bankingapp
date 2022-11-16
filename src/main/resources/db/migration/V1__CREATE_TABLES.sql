create table hibernate_sequence (
    next_val bigint
);

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

CREATE TABLE users
(
    id       BIGSERIAL,
    password varchar(64) not null,
    username varchar(64) not null unique,
    PRIMARY KEY (id)
);

CREATE TABLE wallets
(
    id     BIGSERIAL,
    title  varchar(64) not null,
    amount DOUBLE PRECISION,
    PRIMARY KEY (id)
);