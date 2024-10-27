create table tech_content_user_category
(
    id               bigint       not null auto_increment,
    user_id          bigint       not null,
    categories       varchar(500) not null,
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table tech_content_user_category
    add constraint uk_userid unique (user_id);
