package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkIllegalGroupNameException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkGroupAppender {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    public BookmarkGroupAppender(BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
    }

    public BookmarkGroupId append(UserId userId, String name) {
        if (BookmarkGroupConst.ALL_GROUP_NAME.equals(name)) {
            throw new BookmarkIllegalGroupNameException(userId, name);
        }
        if (name.isBlank()) {
            throw new BookmarkIllegalGroupNameException(userId, name);
        }
        bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name)
                .ifPresent(bookmarkGroupEntity -> {
                    throw new BookmarkIllegalGroupNameException(userId, name);
                });

        return new BookmarkGroupId(
                bookmarkGroupEntityJpaRepository.save(new BookmarkGroupEntity(userId.value(), name)).getId()
        );
    }
}
