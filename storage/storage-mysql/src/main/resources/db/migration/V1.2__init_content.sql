create table tech_content_provider
(
    id               bigint       not null auto_increment,
    icon_id          bigint,
    type             varchar(45)  not null,
    url              varchar(500) not null,
    title            varchar(255) not null,
    deleted          boolean          not null,
    deleted_at       datetime(6),
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table tech_content_provider
    add constraint uk_tech_content_provider__url unique (url);

create table tech_content
(
    id               bigint       not null auto_increment,
    provider_id      bigint       not null,
    image_id         bigint,
    url              varchar(500) not null,
    title            varchar(500) not null,
    content          longtext     not null,
    summary          text         not null,
    published_date   date         not null,
    deleted          boolean          not null,
    deleted_at       datetime(6),
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table tech_content
    add constraint uk_tech_content__url unique (url);

create index ix_tech_content__published_date
    on tech_content (published_date desc);

create index ix_tech_content__provider_published_date
    on tech_content (provider_id, published_date desc);


create table tech_category
(
    created_at       datetime(6) not null,
    id               bigint      not null auto_increment,
    last_modified_at datetime(6) not null,
    tech_content_id  bigint      not null,
    category         varchar(45) not null,
    primary key (id)
) engine = InnoDB;

alter table tech_category
    add constraint uk_tech_category__tech_post_id_name unique (tech_content_id, category);
