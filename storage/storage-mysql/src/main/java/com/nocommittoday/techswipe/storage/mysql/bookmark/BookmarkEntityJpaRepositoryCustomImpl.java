package com.nocommittoday.techswipe.storage.mysql.bookmark;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.nocommittoday.techswipe.storage.mysql.bookmark.QBookmarkEntity.bookmarkEntity;
import static com.querydsl.core.types.dsl.Wildcard.count;

@Repository
class BookmarkEntityJpaRepositoryCustomImpl implements BookmarkEntityJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookmarkEntityJpaRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long countByGroupIdAndContentNotDeleted(Long groupId) {
        return Objects.requireNonNull(
                queryFactory.select(count)
                        .from(bookmarkEntity)
                        .where(
                                bookmarkEntity.group.id.eq(groupId),
                                bookmarkEntity.content.deleted.isFalse(),
                                bookmarkEntity.content.provider.deleted.isFalse()
                        ).fetchOne()
        );
    }

    @Override
    public long countByUserIdAndContentNotDeleted(Long userId) {
        return Objects.requireNonNull(
                queryFactory.select(count)
                        .from(bookmarkEntity)
                        .where(
                                bookmarkEntity.group.userId.eq(userId),
                                bookmarkEntity.content.deleted.isFalse(),
                                bookmarkEntity.content.provider.deleted.isFalse()
                        ).fetchOne()
        );
    }

    @Override
    public List<BookmarkEntity> findAllWithGroupAndContentByGroupIdAndContentNotDeleted(Long groupId) {
        return queryFactory
                .selectFrom(bookmarkEntity)
                .join(bookmarkEntity.group).fetchJoin()
                .join(bookmarkEntity.content).fetchJoin()
                .leftJoin(bookmarkEntity.content.image).fetchJoin()
                .join(bookmarkEntity.content.provider).fetchJoin()
                .leftJoin(bookmarkEntity.content.provider.icon).fetchJoin()
                .where(
                        bookmarkEntity.group.id.eq(groupId),
                        bookmarkEntity.content.deleted.isFalse(),
                        bookmarkEntity.content.provider.deleted.isFalse()
                ).fetch();
    }

    @Override
    public List<BookmarkEntity> findAllByUserIdAndContentNotDeleted(Long userId) {
        return queryFactory
                .selectFrom(bookmarkEntity)
                .join(bookmarkEntity.group).fetchJoin()
                .join(bookmarkEntity.content).fetchJoin()
                .leftJoin(bookmarkEntity.content.image).fetchJoin()
                .join(bookmarkEntity.content.provider).fetchJoin()
                .leftJoin(bookmarkEntity.content.provider.icon).fetchJoin()
                .where(
                        bookmarkEntity.group.userId.eq(userId),
                        bookmarkEntity.content.deleted.isFalse(),
                        bookmarkEntity.content.provider.deleted.isFalse()
                ).fetch();
    }
}
