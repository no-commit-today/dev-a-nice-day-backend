package com.nocommittoday.techswipe.storage.mysql.test;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

public class EntityAppender {

    private final EntityManager em;

    public EntityAppender(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public <T> T append(T entity) {
        em.persist(entity);
        return entity;
    }
}
