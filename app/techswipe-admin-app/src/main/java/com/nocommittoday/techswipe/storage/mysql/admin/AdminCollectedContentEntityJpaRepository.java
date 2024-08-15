package com.nocommittoday.techswipe.storage.mysql.admin;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

@Repository
public class AdminCollectedContentEntityJpaRepository {

    private final JPAQueryFactory queryFactory;

    public AdminCollectedContentEntityJpaRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean updateSummaryById(Long id, String summary) {
        return queryFactory.update(collectedContentEntity)
                .set(collectedContentEntity.summary, summary)
                .where(collectedContentEntity.id.eq(id))
                .execute() == 1;
    }
}
