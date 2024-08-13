create table users
(
    id               bigint      not null auto_increment,
    deleted          boolean     not null,
    deleted_at       datetime(6),
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    primary key (id)
) engine = InnoDB;


create table oauth2_users
(
    id               bigint       not null auto_increment,
    oauth2_provider  varchar(25)  not null,
    oauth2_id        varchar(100) not null,
    user_id          bigint       not null,
    deleted          boolean      not null,
    deleted_at       datetime(6),
    created_at       datetime(6)  not null,
    last_modified_at datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

alter table oauth2_users
    add constraint uk_oauth2provider_oauth2id unique (oauth2_provider, oauth2_id);


create table logged_in
(
    id               bigint      not null auto_increment,
    user_id          bigint      not null,
    refresh_token_id varchar(65) not null,
    expires_at       datetime(6) not null,
    deleted          boolean     not null,
    deleted_at       datetime(6),
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    primary key (id)
) engine = InnoDB;

alter table logged_in
    add constraint uk_refreshtokenid unique (refresh_token_id);

create index ix_userid on logged_in (user_id);
