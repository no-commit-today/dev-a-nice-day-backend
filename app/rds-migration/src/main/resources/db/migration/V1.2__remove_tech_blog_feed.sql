drop table if exists tech_blog_scrapping;

alter table tech_blog drop column feed_type;
alter table tech_blog drop column feed_link;

alter table tech_blog add column icon_url varchar(1000) after link;