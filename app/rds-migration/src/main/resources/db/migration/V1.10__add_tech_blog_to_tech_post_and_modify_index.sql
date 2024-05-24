alter table tech_post drop index ix_tech_post__published_at;

alter table tech_post add column tech_blog_id bigint after published_date;

create index ix_tech_post__published_date on tech_post (published_date desc);
create index ix_tech_post__tech_blog_id_published_date on tech_post(tech_blog_id, published_date desc);
