package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

@Repository
class CollectedContentJpaRepositoryCustomImpl implements CollectedContentJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CollectedContentJpaRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findAllUrl() {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .fetch();
    }

    @Override
    public List<String> findAllUrlByProvider(TechContentProviderEntity provider) {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .where(collectedContentEntity.provider.eq(provider))
                .fetch();
    }

    @Override
    public List<String> findAllUrlByProviderIn(Collection<TechContentProviderEntity> providers) {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .where(collectedContentEntity.provider.in(providers))
                .fetch();
    }

    @Override
    public List<String> findAllUrlByUrlIn(Collection<String> urls) {
        return queryFactory
                .select(collectedContentEntity.url)
                .from(collectedContentEntity)
                .where(collectedContentEntity.url.in(urls))
                .fetch();
    }
}
