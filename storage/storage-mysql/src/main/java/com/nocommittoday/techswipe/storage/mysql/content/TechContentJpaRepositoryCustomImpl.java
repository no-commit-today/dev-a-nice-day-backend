package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.LocalDate;
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
    public List<TechContentEntity> findAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
            PageParam pageParam, List<TechCategory> categories) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentCategoryEntity).on(
                        techContentEntity.id.eq(techContentCategoryEntity.content.id))
                .where(
                        techContentCategoryEntity.category.in(categories),
                        techContentEntity.deleted.isFalse()
                )
                .groupBy(techContentEntity.publishedDate, techContentEntity.id)
                .orderBy(techContentEntity.publishedDate.desc())
                .offset(pageParam.offset())
                .limit(pageParam.size())
                .fetch();
    }

    @Override
    public List<TechContentEntity> findAllByGreaterThanContentCategoryInOrderByPublishedDateDescAndDeletedIsFalse(
            @Nullable TechContentId lastContentId, List<TechCategory> categories, int size) {
        return queryFactory
                .selectFrom(techContentEntity)
                .join(techContentCategoryEntity).on(
                        techContentEntity.id.eq(techContentCategoryEntity.content.id)
                )
                .where(
                        techContentCategoryEntity.category.in(categories),
                        lastContentCondition(lastContentId),
                        techContentEntity.deleted.isFalse()
                )
                .orderBy(
                        techContentEntity.publishedDate.desc(),
                        techContentEntity.id.asc()
                )
                .groupBy(
                        techContentEntity.publishedDate,
                        techContentEntity.id
                )
                .limit(size)
                .fetch();
    }

    @Nullable
    private BooleanExpression lastContentCondition(@Nullable TechContentId lastContentId) {
        if (lastContentId == null) {
            return null;
        }

        JPQLQuery<LocalDate> lastContentPublishedDate = JPAExpressions.select(techContentEntity.publishedDate)
                .from(techContentEntity)
                .where(techContentEntity.id.eq(lastContentId.value()));

        return techContentEntity.publishedDate.eq(lastContentPublishedDate).and(
                        techContentEntity.id.gt(lastContentId.value()))
                .or(techContentEntity.publishedDate.lt(lastContentPublishedDate));
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
