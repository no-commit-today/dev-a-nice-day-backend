package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.bookmark.exception.BookmarkIllegalGroupNameException;
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
        if (
                name.isBlank()
                || bookmarkGroupEntityJpaRepository.findByUserIdAndName(userId.value(), name).isPresent()
        ) {
            throw new BookmarkIllegalGroupNameException(userId, name);
        }

        return new BookmarkGroupId(
                bookmarkGroupEntityJpaRepository.save(new BookmarkGroupEntity(userId.value(), name)).getId()
        );
    }
}
