alter table collected_content
    add column published_content_id bigint after subscription_id;

update collected_content
set published_content_id = id
where status = 'PUBLISHED';
