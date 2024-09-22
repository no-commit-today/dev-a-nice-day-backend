package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkRemover {

    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkRemover(BookmarkEntityJpaRepository bookmarkEntityJpaRepository) {
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    public void remove(BookmarkGroupId groupId, TechContentId contentId) {
        bookmarkEntityJpaRepository.findByGroupIdAndContentId(groupId.value(), contentId.value())
                .ifPresent(bookmarkEntityJpaRepository::delete);
    }
}
