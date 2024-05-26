create table images
(
    id               bigint        not null auto_increment,
    url              varchar(1000) not null,
    original_url     varchar(1000) not null,
    stored_name      varchar(1000) not null,
    created_at       datetime(6)   not null,
    last_modified_at datetime(6)   not null,
    primary key (id)
) engine = InnoDB;