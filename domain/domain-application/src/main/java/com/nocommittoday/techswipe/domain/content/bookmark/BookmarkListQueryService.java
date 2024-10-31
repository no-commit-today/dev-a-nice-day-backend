package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class BookmarkListQueryService {

    private final BookmarkGroupReader bookmarkGroupReader;
    private final BookmarkListQueryReader bookmarkListQueryReader;

    public BookmarkListQueryService(
            BookmarkGroupReader bookmarkGroupReader,
            BookmarkListQueryReader bookmarkListQueryReader
    ) {
        this.bookmarkGroupReader = bookmarkGroupReader;
        this.bookmarkListQueryReader = bookmarkListQueryReader;
    }

    public BookmarkListQueryResult getList(ApiUser user, @Nullable String groupName) {
        if (groupName == null) {
            return new BookmarkListQueryResult(bookmarkListQueryReader.getAllList(user.getUserId()));
        }
        BookmarkGroup group = bookmarkGroupReader.read(user.getUserId(), groupName);
        return new BookmarkListQueryResult(bookmarkListQueryReader.getList(group));
    }
}
