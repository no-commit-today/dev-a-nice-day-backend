alter table subscription
    drop index uk_subscription__provider_id;
create index ix_providerid on subscription (provider_id);

alter table subscription
    add column feed json after type;

alter table subscription
    add column list_scrapping json after feed;

update subscription
set feed = JSON_OBJECT(
        'url', JSON_EXTRACT(data, '$.feed.url'),
        'contentScrapping', JSON_OBJECT(
                'title', JSON_OBJECT(
                        'type', JSON_EXTRACT(data, '$.contentCrawling.title.type'),
                        'selector', JSON_EXTRACT(data, '$.contentCrawling.title.selector'),
                        'date', JSON_EXTRACT(data, '$.contentCrawling.title.indexes')
                         ),
                'date', JSON_OBJECT(
                        'type', JSON_EXTRACT(data, '$.contentCrawling.date.type'),
                        'selector', JSON_EXTRACT(data, '$.contentCrawling.date.selector'),
                        'date', JSON_EXTRACT(data, '$.contentCrawling.date.indexes')
                        ),
                'content', JSON_OBJECT(
                        'type', JSON_EXTRACT(data, '$.contentCrawling.content.type'),
                        'selector', JSON_EXTRACT(data, '$.contentCrawling.content.selector'),
                        'date', JSON_EXTRACT(data, '$.contentCrawling.content.indexes')
                        ),
                'image', JSON_OBJECT(
                        'type', 'OPEN_GRAPH_IMAGE',
                        'selector', NULL,
                        'date', NULL
                        )
                )
        );

alter table subscription
    drop column data;
alter table subscription
    drop column init_type;

alter table collected_content
    add column subscription_id bigint not null after provider_id;

update collected_content set subscription_id = (select id from subscription where subscription.provider_id = collected_content.provider_id);
