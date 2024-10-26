package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarkGroupListQueryReader {

    private final BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    public BookmarkGroupListQueryReader(BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository) {
        this.bookmarkGroupEntityJpaRepository = bookmarkGroupEntityJpaRepository;
    }

    public List<BookmarkGroupQuery> getList(UserId userId) {
        return bookmarkGroupEntityJpaRepository.findAllByUserId(userId.value())
                .stream()
                .map(BookmarkGroupEntity::toQuery)
                .toList();
    }
}
