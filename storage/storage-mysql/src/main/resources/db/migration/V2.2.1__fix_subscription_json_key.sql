update subscription
set feed = JSON_SET(
        feed,
        '$.contentScrapping.title.indexes', COALESCE(JSON_EXTRACT(feed, '$.contentScrapping.title.date'), CAST(NULL AS JSON)),
        '$.contentScrapping.date.indexes', COALESCE(JSON_EXTRACT(feed, '$.contentScrapping.date.date'), CAST(NULL AS JSON)),
        '$.contentScrapping.content.indexes', COALESCE(JSON_EXTRACT(feed, '$.contentScrapping.content.date'), CAST(NULL AS JSON)),
        '$.contentScrapping.image.indexes', COALESCE(JSON_EXTRACT(feed, '$.contentScrapping.image.date'), CAST(NULL AS JSON))
        );

update subscription
set feed = JSON_REMOVE(
        feed,
        '$.contentScrapping.title.date',
        '$.contentScrapping.date.date',
        '$.contentScrapping.content.date',
        '$.contentScrapping.image.date'
        );