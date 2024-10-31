package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkCountReader {

    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkCountReader(BookmarkEntityJpaRepository bookmarkEntityJpaRepository) {
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    public int count(BookmarkGroup group) {
        if (group.getId() == null) {
            return (int) bookmarkEntityJpaRepository.countByUserIdAndContentNotDeleted(group.getUserId().value());
        }

        return (int) bookmarkEntityJpaRepository.countByGroupIdAndContentNotDeleted(group.getId().value());
    }
}
