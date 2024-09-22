package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class BookmarkGroupQueryReader {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    public BookmarkGroupQueryReader(BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
    }

    public BookmarkGroupQuery read(UserId userId, @Nullable String name) {
        if (name == null || name.equals(BookmarkGroupConst.ALL_GROUP_NAME)) {
            return BookmarkGroupQuery.allGroup(userId);
        }

        return bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name)
                .map(BookmarkGroupEntity::toQuery)
                .orElseThrow(() -> new BookmarkGroupNotFoundException(userId, name));
    }
}
