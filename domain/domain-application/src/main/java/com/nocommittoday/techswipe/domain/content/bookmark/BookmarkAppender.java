package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.bookmark.exception.BookmarkAlreadyExistsException;
import com.nocommittoday.techswipe.domain.content.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BookmarkAppender {

    private final TechContentJpaRepository techContentJpaRepository;
    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;
    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;


    public BookmarkAppender(
            TechContentJpaRepository techContentJpaRepository,
            BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository,
            BookmarkEntityJpaRepository bookmarkEntityJpaRepository
    ) {
        this.techContentJpaRepository = techContentJpaRepository;
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    public BookmarkId append(BookmarkGroupId groupId, TechContentId contentId) {
        BookmarkGroupEntity bookmarkGroup = bookmarkGroupEntityJpaRepository.findById(groupId.value())
                .orElseThrow(() -> new BookmarkGroupNotFoundException(groupId));
        TechContentEntity content = techContentJpaRepository.findById(contentId.value())
                .filter(TechContentEntity::isUsed)
                .orElseThrow(() -> new TechContentNotFoundException(contentId));

        if (bookmarkEntityJpaRepository.findByGroupAndContent(bookmarkGroup, content).isPresent()) {
            throw new BookmarkAlreadyExistsException(groupId, contentId);
        }

        return new BookmarkId(
                bookmarkEntityJpaRepository.save(new BookmarkEntity(bookmarkGroup, content)).getId()
        );
    }
}
