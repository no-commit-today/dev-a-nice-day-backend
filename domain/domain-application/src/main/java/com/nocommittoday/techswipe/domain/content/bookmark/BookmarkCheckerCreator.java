package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarkCheckerCreator {

    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkCheckerCreator(BookmarkEntityJpaRepository bookmarkEntityJpaRepository) {
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    public BookmarkChecker create(UserId userId, List<TechContentId> contentIds) {
        List<Bookmark> bookmarks = bookmarkEntityJpaRepository.findAllByUserIdAndContentIdIn(
                userId.value(), contentIds.stream().map(TechContentId::value).toList()
        ).stream().map(BookmarkEntity::toDomain).toList();

        return BookmarkChecker.create(bookmarks);
    }

    public BookmarkChecker create(UserId userId, TechContentId contentId) {
        return create(userId, List.of(contentId));
    }
}
