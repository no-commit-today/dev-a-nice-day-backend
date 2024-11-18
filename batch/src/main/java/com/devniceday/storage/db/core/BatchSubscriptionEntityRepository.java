package com.devniceday.storage.db.core;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BatchSubscriptionEntityRepository {

    private final EntityManager em;

    public BatchSubscriptionEntityRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void disable(BatchSubscriptionEntity entity) {
        entity.disable();
        em.merge(entity);
    }
}
