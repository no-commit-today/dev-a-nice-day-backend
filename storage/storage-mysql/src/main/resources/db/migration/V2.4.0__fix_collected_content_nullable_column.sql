alter table collected_content
    modify column title varchar(500);

alter table collected_content
    modify column published_date date;

alter table collected_content
    modify column content longtext;
