package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookmarkListQueryReader {

    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkListQueryReader(BookmarkEntityJpaRepository bookmarkEntityJpaRepository) {
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<BookmarkQuery> getList(BookmarkGroup group) {
        if (group.getId() == null) {
            return bookmarkEntityJpaRepository.findAllByUserIdAndContentNotDeleted(group.getUserId().value())
                    .stream()
                    .map(BookmarkEntity::toQuery)
                    .toList();
        }

        return bookmarkEntityJpaRepository.findAllWithGroupAndContentByGroupIdAndContentNotDeleted(group.getId().value())
                .stream()
                .map(BookmarkEntity::toQuery)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookmarkQuery> getAllList(UserId userId) {
        return bookmarkEntityJpaRepository.findAllByUserIdAndContentNotDeleted(userId.value())
                .stream()
                .map(BookmarkEntity::toQuery)
                .toList();
    }
}
