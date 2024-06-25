package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.nocommittoday.techswipe.collection.storage.mysql.QCollectedContentEntity.collectedContentEntity;

@Repository
class CollectedContentJpaRepositoryCustomImpl implements CollectedContentJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CollectedContentJpaRepositoryCustomImpl(final EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findAllUrlByProvider(final TechContentProviderEntity provider) {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .where(collectedContentEntity.provider.eq(provider))
                .fetch();
    }

    @Override
    public List<String> findAllUrlByProviderIn(final Collection<TechContentProviderEntity> providers) {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .where(collectedContentEntity.provider.in(providers))
                .fetch();
    }
}
