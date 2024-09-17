package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkGroupAlreadyExistsException;
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
        bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name)
                .ifPresent(bookmarkGroupEntity -> {
                    throw new BookmarkGroupAlreadyExistsException(userId, name);
                });

        return new BookmarkGroupId(
                bookmarkGroupEntityJpaRepository.save(new BookmarkGroupEntity(userId.value(), name)).getId()
        );
    }
}
