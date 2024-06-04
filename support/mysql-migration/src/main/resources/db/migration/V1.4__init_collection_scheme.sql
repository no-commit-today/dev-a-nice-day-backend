create table collected_content
(
    id               bigint       not null auto_increment,
    type             varchar(45)  not null,
    provider_id      bigint       not null,
    url              varchar(500) not null,
    title            varchar(500) not null,
    content          longtext     not null,
    image_url        varchar(500),
    categories       varchar(500),
    deleted          boolean      not null,
    deleted_at       datetime(6),
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table collected_content
    add constraint uk_collected_content__url unique (url);

alter table tech_content drop column content;