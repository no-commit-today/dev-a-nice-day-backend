package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentCategoryEntity.techContentCategoryEntity;
import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentEntity.techContentEntity;


@Repository
class TechContentJpaQueryRepositoryCustomImpl implements TechContentJpaQueryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TechContentJpaQueryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TechContentEntity> findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(PageParam pageParam, List<TechCategory> categories) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentEntity.provider).fetchJoin()
                .leftJoin(techContentEntity.image).fetchJoin()
                .join(techContentCategoryEntity).on(
                        techContentEntity.id.eq(techContentCategoryEntity.content.id))
                .where(
                        techContentCategoryEntity.category.in(categories),
                        techContentEntity.deleted.isFalse(),
                        techContentEntity.provider.deleted.isFalse()
                )
                .groupBy(techContentEntity.publishedDate, techContentEntity.id)
                .orderBy(techContentEntity.publishedDate.desc())
                .offset(pageParam.offset())
                .limit(pageParam.size())
                .fetch();
    }
}
