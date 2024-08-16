package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

class AdminCollectedContentEntityJpaRepositoryCustomImpl implements AdminCollectedContentEntityJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdminCollectedContentEntityJpaRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean updateSummaryById(Long id, String summary) {
        return queryFactory.update(collectedContentEntity)
                .set(collectedContentEntity.summary, summary)
                .where(collectedContentEntity.id.eq(id))
                .execute() == 1;
    }

    public boolean updateStatusAndCategoriesById(Long id, CollectionStatus status, List<CollectionCategory> categories) {
        return queryFactory.update(collectedContentEntity)
                .set(collectedContentEntity.status, status)
                .set(collectedContentEntity.categories, categories)
                .where(collectedContentEntity.id.eq(id))
                .execute() == 1;
    }
}
