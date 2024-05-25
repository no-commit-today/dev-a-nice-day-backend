package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nocommittoday.techswipe.content.infrastructure.mysql.QTechCategoryEntity.techCategoryEntity;


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
                .select(techCategoryEntity.content)
                .from(techCategoryEntity)
                .join(techCategoryEntity.content).fetchJoin()
                .join(techCategoryEntity.content.provider).fetchJoin()
                .where(techCategoryEntity.category.in(categories))
                .orderBy(techCategoryEntity.content.publishedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
