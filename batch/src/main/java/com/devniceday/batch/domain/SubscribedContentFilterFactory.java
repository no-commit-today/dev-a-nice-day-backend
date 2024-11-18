package com.devniceday.batch.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.devniceday.storage.db.core.QBatchCollectedContentEntity.batchCollectedContentEntity;

@Component
public class SubscribedContentFilterFactory {

    private final JPAQueryFactory queryFactory;

    public SubscribedContentFilterFactory(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public SubscribedContentFilter create(List<SubscribedContent> contents) {
        List<String> urls = contents.stream()
                .map(SubscribedContent::url)
                .toList();
        Set<String> collectedUrlSet = new HashSet<>(
                queryFactory.select(batchCollectedContentEntity.url)
                        .from(batchCollectedContentEntity)
                        .where(batchCollectedContentEntity.url.in(urls))
                        .fetch());
        return new SubscribedContentFilter(collectedUrlSet, contents);
    }
}
