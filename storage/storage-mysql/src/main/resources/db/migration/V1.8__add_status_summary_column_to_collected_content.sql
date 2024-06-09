alter table collected_content
    add column status varchar(45) not null after type;

alter table collected_content
    add column summary varchar(2000) after categories;
