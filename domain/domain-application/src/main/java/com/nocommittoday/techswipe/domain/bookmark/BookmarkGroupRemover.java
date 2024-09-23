package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookmarkGroupRemover {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;
    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkGroupRemover(
            BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository,
            BookmarkEntityJpaRepository bookmarkEntityJpaRepository
    ) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    @Transactional
    public void remove(UserId userId, String groupName) {
        BookmarkGroupEntity groupEntity = bookmarkGroupEntityJpaRepository.findByUserIdAndName(
                        userId.value(), groupName)
                .orElseThrow(() -> new BookmarkGroupNotFoundException(userId, groupName));

        bookmarkEntityJpaRepository.deleteAll(bookmarkEntityJpaRepository.findAllByGroup(groupEntity));
        bookmarkGroupEntityJpaRepository.delete(groupEntity);
    }
}
