create table prompt
(
    id               bigint        not null auto_increment,
    type             varchar(45)   not null,
    provider_type    varchar(45)   not null,
    prompt_version   varchar(25)   not null,
    model            varchar(255)  not null,
    content          varchar(2000) not null,
    deleted          boolean       not null,
    deleted_at       datetime(6),
    created_at       datetime(6)   not null,
    last_modified_at datetime(6)   not null,
    primary key (id)
) engine = InnoDB;
