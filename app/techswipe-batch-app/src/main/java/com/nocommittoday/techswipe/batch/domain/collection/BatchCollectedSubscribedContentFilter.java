package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

public class BatchCollectedSubscribedContentFilter {

    private static final Logger log = LoggerFactory.getLogger(BatchCollectedSubscribedContentFilter.class);

    private final Set<String> urlSet;

    private final List<SubscribedContent> contents;

    BatchCollectedSubscribedContentFilter(Set<String> urlSet, List<SubscribedContent> contents) {
        this.urlSet = urlSet;
        this.contents = contents;
    }

    public static BatchCollectedSubscribedContentFilter create(
            JPAQueryFactory queryFactory, List<SubscribedContent> contents) {
        List<String> urls = contents.stream()
                .map(SubscribedContent::getUrl)
                .toList();
        Set<String> collectedUrlSet = new HashSet<>(
                queryFactory.select(collectedContentEntity.url)
                        .from(collectedContentEntity)
                        .where(collectedContentEntity.url.in(urls))
                        .fetch());
        return new BatchCollectedSubscribedContentFilter(collectedUrlSet, contents);
    }

    public boolean hasNext() {
        return urlSet.isEmpty() && !contents.isEmpty();
    }

    public List<SubscribedContent> filter() {
        List<SubscribedContent> filtered = contents.stream()
                .filter(content -> !urlSet.contains(content.getUrl()))
                .toList();
        filtered.forEach(content ->
                log.debug("subscriptionId[{}] url[{}] 이 존재합니다.",
                        content.getSubscriptionId(), content.getUrl())
        );
        return filtered;
    }
}
