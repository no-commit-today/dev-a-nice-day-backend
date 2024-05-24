create table url_image
(
    url              varchar(1000) not null,
    original_url     varchar(1000) not null,
    stored_name      varchar(1000) not null,
    created_at       timestamp(6)  not null,
    last_modified_at timestamp(6)  not null,
    id               bigint auto_increment,
    primary key (id)
);


alter table tech_blog add column icon_id bigint after icon_url;
alter table tech_blog drop column icon_url;
alter table tech_blog drop column image_url;