package com.devniceday.storage.db.core;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.devniceday.storage.db.core.QBatchSubscriptionEntity.batchSubscriptionEntity;

@Repository
public class BatchSubscriptionEntityRepository {

    private final JPAQueryFactory queryFactory;

    public BatchSubscriptionEntityRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public void disable(long id) {
        queryFactory.update(batchSubscriptionEntity)
                .set(batchSubscriptionEntity.disabled, true)
                .where(batchSubscriptionEntity.id.eq(id))
                .execute();
    }
}
