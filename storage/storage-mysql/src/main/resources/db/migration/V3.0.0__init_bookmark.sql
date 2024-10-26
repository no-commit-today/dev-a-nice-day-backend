create table bookmark_group
(
    id               bigint       not null auto_increment,
    user_id          bigint       not null,
    name             varchar(255) not null,
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table bookmark_group
    add constraint uk_userid_name unique (user_id, name);

create table bookmark
(
    id               bigint      not null auto_increment,
    group_id         bigint      not null,
    content_id       bigint      not null,
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    primary key (id)
) engine = InnoDB;

alter table bookmark
    add constraint uk_groupid_contentid unique (group_id, content_id);

create index ix_groupid_createdat
    on bookmark (group_id, created_at desc);
