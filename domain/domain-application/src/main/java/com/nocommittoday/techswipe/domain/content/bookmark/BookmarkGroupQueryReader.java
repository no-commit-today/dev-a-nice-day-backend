package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGroupQueryReader {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    public BookmarkGroupQueryReader(BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
    }

    public BookmarkGroupQuery read(UserId userId, String name) {

        return bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name)
                .map(BookmarkGroupEntity::toQuery)
                .orElseThrow(() -> new BookmarkGroupNotFoundException(userId, name));
    }
}
