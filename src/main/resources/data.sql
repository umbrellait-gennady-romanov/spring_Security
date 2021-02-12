drop table if exists users cascade ;
drop table if exists roles cascade ;
drop table if exists users_roles cascade ;

create table users (
 id         bigserial,
 name   varchar(30) not null,
 password   varchar(80),
 email      varchar(50) UNIQUE,
 primary key (id)
);

create table roles (
 id       serial,
 name     varchar(50) not null,
 primary key (id)
);

create table users_roles (
 user_id    bigint not null,
 role_id    int not null,
 primary key (user_id, role_id),
 foreign key (user_id) references users (id),
 foreign key (role_id) references roles (id)
);

insert into roles (name) values
('ROLE_USER'), ('ROLE_ADMIN');

insert into users (name, password, email) values
('user', '$2y$12$AwazSWTcOqAeRmJAnXyf0uLZ78OQICYF.NT4jJe6AuLtMnIm9pC/i','user@gmail.ru');

insert into users_roles (user_id, role_id) values
(1, 2);