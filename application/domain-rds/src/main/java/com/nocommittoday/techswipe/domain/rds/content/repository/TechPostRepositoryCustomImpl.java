package com.nocommittoday.techswipe.domain.rds.content.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

import static com.nocommittoday.techswipe.domain.rds.content.QTechPost.techPost;

public class TechPostRepositoryCustomImpl implements TechPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TechPostRepositoryCustomImpl(final EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findAllUrlByUrlIn(final Collection<String> urls) {
        return queryFactory
                .select(techPost.url)
                .from(techPost)
                .where(techPost.url.in(urls))
                .fetch();
    }
}
