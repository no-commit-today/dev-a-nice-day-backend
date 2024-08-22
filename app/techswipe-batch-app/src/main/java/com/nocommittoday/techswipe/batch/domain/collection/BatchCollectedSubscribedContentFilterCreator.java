package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchCollectedSubscribedContentFilterCreator {

    private final JPAQueryFactory queryFactory;

    public BatchCollectedSubscribedContentFilterCreator(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public BatchCollectedSubscribedContentFilter create(List<SubscribedContent> contents) {
        return BatchCollectedSubscribedContentFilter.create(queryFactory, contents);
    }
}
