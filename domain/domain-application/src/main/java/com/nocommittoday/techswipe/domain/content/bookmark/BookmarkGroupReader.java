package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGroupReader {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    public BookmarkGroupReader(BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
    }

    public BookmarkGroup read(UserId userId, String name) {
        return bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name)
                .map(BookmarkGroupEntity::toDomain)
                .orElseThrow(() -> new BookmarkGroupNotFoundException(userId, name));
    }
}
