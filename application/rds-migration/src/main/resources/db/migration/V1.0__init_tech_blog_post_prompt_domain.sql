create table tech_post
(
    uid              varchar(45)   not null,
    link             varchar(500)  not null,
    title            varchar(1000) not null,
    content          longtext      not null,
    published_at     timestamp(6)  not null,
    summary          text          not null,
    deleted          tinyint(1)    not null,
    deleted_at       timestamp(6),
    created_at       timestamp(6)  not null,
    last_modified_at timestamp(6)  not null,
    id               bigint auto_increment,
    primary key (id)
);

alter table tech_post
    add constraint uk_tech_post__uid unique (uid);

alter table tech_post
    add constraint uk_tech_post__link unique (link);

create index ix_tech_post__published_at on tech_post (published_at);


create table tech_post_category
(
    tech_post_id bigint      not null,
    category     varchar(45) not null,
    id           bigint auto_increment,
    primary key (id)
);

alter table tech_post_category
    add constraint uk_tech_post_category__tech_post_id_category unique (tech_post_id, category);


create table tech_post_keyword
(
    keyword_id   bigint not null,
    tech_post_id bigint not null,
    id           bigint auto_increment,
    primary key (id)
);

alter table tech_post_keyword
    add constraint uk_tech_post_keyword__post_id_keyword_id unique (tech_post_id, keyword_id);


create table tech_keyword
(
    created_at       timestamp(6) not null,
    id               bigint auto_increment,
    last_modified_at timestamp(6) not null,
    name             varchar(45)  not null,
    primary key (id)
);

alter table tech_keyword
    add constraint uk_tech_keyword__name unique (name);


create table generation_history
(
    prompt_id        bigint       not null,
    tech_post_id     bigint       not null,
    created_at       timestamp(6) not null,
    last_modified_at timestamp(6) not null,
    id               bigint auto_increment,
    primary key (id)
);


create table prompt
(
    version          varchar(25)  not null,
    type             varchar(45)  not null,
    content          text         not null,
    created_at       timestamp(6) not null,
    last_modified_at timestamp(6) not null,
    id               bigint auto_increment,
    primary key (id)
);

alter table prompt
    add constraint uk_prompt__type_version unique (type, version);


create table tech_blog
(
    type             varchar(45)   not null,
    feed_type        varchar(45)   not null,
    title            varchar(255)  not null,
    feed_link        varchar(1000) not null,
    link             varchar(1000) not null,
    deleted          tinyint(1)    not null,
    deleted_at       timestamp(6),
    created_at       timestamp(6)  not null,
    last_modified_at timestamp(6)  not null,
    id               bigint auto_increment,
    primary key (id)
);

create table tech_blog_scrapping
(
    name           varchar(255) not null,
    title          varchar(255),
    published_date varchar(255),
    content        varchar(255),
    id             bigint auto_increment,
    primary key (id)
);
