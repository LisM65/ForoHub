create table topicos
(
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje text not null,
    autor varchar(15)  not null unique,
    curso varchar(20)  not null,

    primary key (id)
);