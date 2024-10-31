package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class BookmarkListQueryService {

    private final BookmarkGroupQueryReader bookmarkGroupQueryReader;
    private final BookmarkListQueryReader bookmarkListQueryReader;

    public BookmarkListQueryService(
            BookmarkGroupQueryReader bookmarkGroupQueryReader,
            BookmarkListQueryReader bookmarkListQueryReader
    ) {
        this.bookmarkGroupQueryReader = bookmarkGroupQueryReader;
        this.bookmarkListQueryReader = bookmarkListQueryReader;
    }

    public BookmarkListQueryResult getList(ApiUser user, @Nullable String groupName) {
        if (groupName == null) {
            return new BookmarkListQueryResult(bookmarkListQueryReader.getAllList(user.getUserId()));
        }
        BookmarkGroupQuery group = bookmarkGroupQueryReader.read(user.getUserId(), groupName);
        return new BookmarkListQueryResult(bookmarkListQueryReader.getList(group));
    }
}
