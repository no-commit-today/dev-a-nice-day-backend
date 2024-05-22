alter table tech_post drop index uk_tech_post__link;

alter table tech_post change link url varchar(500) not null;
alter table tech_post change title title varchar(500) not null;

alter table tech_post
    add constraint uk_tech_post__url unique (url);

alter table tech_blog change url url varchar(500) not null;

alter table tech_blog
    add constraint uk_tech_blog__url unique (url);