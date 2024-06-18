package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nocommittoday.techswipe.content.storage.mysql.QTechCategoryEntity.techCategoryEntity;
import static com.nocommittoday.techswipe.content.storage.mysql.QTechContentEntity.techContentEntity;


@Repository
class TechContentJpaRepositoryCustomImpl implements TechContentJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TechContentJpaRepositoryCustomImpl(final EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TechContentEntity> findAllWithProviderByCategoryInOrderByPublishedDateDesc(
            final Pageable pageable, final List<TechCategory> categories
    ) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentEntity.provider).fetchJoin()
                .join(techCategoryEntity).on(
                        techContentEntity.id.eq(techCategoryEntity.content.id))
                .where(techCategoryEntity.category.in(categories))
                .groupBy(techContentEntity.publishedDate, techContentEntity.id)
                .orderBy(techContentEntity.publishedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(final Pageable pageable) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentEntity).fetchJoin()
                .join(techContentEntity.provider).fetchJoin()
                .orderBy(techContentEntity.publishedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
