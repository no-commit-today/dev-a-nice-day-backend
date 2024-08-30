package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentCategoryEntity.techContentCategoryEntity;
import static com.nocommittoday.techswipe.storage.mysql.content.QTechContentEntity.techContentEntity;


@Repository
class TechContentJpaRepositoryCustomImpl implements TechContentJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TechContentJpaRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TechContentEntity> findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(PageParam pageParam, List<TechCategory> categories) {
        return queryFactory
                .selectFrom(techContentEntity)
                .leftJoin(techContentEntity.image).fetchJoin()
                .join(techContentEntity.provider).fetchJoin()
                .leftJoin(techContentEntity.provider.icon).fetchJoin()
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

    @Override
    public long countByCategoryInAndDeletedIsFalse(List<TechCategory> categories) {
        return Objects.requireNonNull(
                queryFactory
                        .select(techContentCategoryEntity.content.countDistinct())
                        .from(techContentCategoryEntity)
                        .where(
                                techContentCategoryEntity.category.in(categories),
                                techContentCategoryEntity.content.deleted.isFalse()
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(PageParam pageParam) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentEntity.provider).fetchJoin()
                .where(techContentEntity.deleted.isFalse())
                .orderBy(techContentEntity.publishedDate.desc())
                .offset(pageParam.offset())
                .limit(pageParam.size())
                .fetch();
    }

    @Override
    public Optional<String> findUrlByIdAndDeletedIsFalse(Long id) {
        return Optional.ofNullable(
                queryFactory
                        .select(techContentEntity.url)
                        .from(techContentEntity)
                        .where(
                                techContentEntity.id.eq(id),
                                techContentEntity.deleted.isFalse()
                        )
                        .fetchOne()
        );
    }
}
