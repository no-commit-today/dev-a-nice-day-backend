alter table images
    add column deleted boolean not null after stored_name;

alter table images
    add column deleted_at datetime(6) not null after deleted;
