show databases ;

create database springDemo;

use springDemo;

show tables;

drop table users;
drop table authorities;

create table users(
                      username varchar(50) not null primary key,
                      password varchar(500) not null ,
                      enabled boolean not null
);

create table authorities(
                            username varchar(50) not null ,
                            authority varchar(50) not null ,
                            constraint fk_1 foreign key (username) references users(username)
);

select * from users;

select * from authorities;

