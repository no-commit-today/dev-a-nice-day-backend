package com.devniceday.storage.db.core;

import com.devniceday.batch.domain.CollectionStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.devniceday.storage.db.core.QBatchCollectedContentEntity.batchCollectedContentEntity;

@Repository
public class BatchCollectedContentEntityRepository {

    private final JPAQueryFactory queryFactory;

    public BatchCollectedContentEntityRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public void filter(long id) {
        queryFactory.update(batchCollectedContentEntity)
                .set(batchCollectedContentEntity.status, CollectionStatus.FILTERED)
                .where(batchCollectedContentEntity.id.eq(id))
                .execute();
    }
}
