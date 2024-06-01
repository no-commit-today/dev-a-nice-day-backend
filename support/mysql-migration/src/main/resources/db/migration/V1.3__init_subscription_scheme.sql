create table subscription
(
    id               bigint      not null auto_increment,
    provider_id      bigint      not null,
    type             varchar(45) not null,
    data             json        not null,
    deleted          boolean     not null,
    deleted_at       datetime(6),
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    primary key (id)
) engine = InnoDB;

alter table subscription
    add constraint uk_subscription__provider_id unique (provider_id);
