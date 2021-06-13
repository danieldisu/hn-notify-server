create table hn_user
(
    id varchar(255) not null,
    primary key (id)
);

create table interest
(
    id            varchar(255) not null,
    added_on      date,
    interest_name varchar(255),
    user_id       varchar(255),
    primary key (id),
    UNIQUE (interest_name, user_id)
);

create table story
(
    id    varchar(255) not null,
    title varchar(255),
    primary key (id)
);

INSERT INTO hn_user
VALUES ('1');

INSERT INTO hn_user
VALUES ('2');
