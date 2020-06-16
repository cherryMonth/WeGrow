show databases;

create database springDemo;

use springDemo;

show tables;

drop table users;

create table users
(
    id       bigint(20)   not null auto_increment primary key,
    username varchar(50)  not null,
    password varchar(500) not null,
    enabled  boolean      not null comment '用户是否可用',
    roles    text character set utf8 comment '用户角色, 多个角色之间用逗号隔开'
);

select *
from users;


