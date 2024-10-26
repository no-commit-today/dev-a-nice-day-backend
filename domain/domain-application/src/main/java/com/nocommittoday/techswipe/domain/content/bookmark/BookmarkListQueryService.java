package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class BookmarkListQueryService {

    private final BookmarkGroupQueryReader bookmarkGroupQueryReader;
    private final BookmarkCountQueryReader bookmarkCountQueryReader;
    private final BookmarkListQueryReader bookmarkListQueryReader;

    public BookmarkListQueryService(
            BookmarkGroupQueryReader bookmarkGroupQueryReader,
            BookmarkCountQueryReader bookmarkCountQueryReader,
            BookmarkListQueryReader bookmarkListQueryReader
    ) {
        this.bookmarkGroupQueryReader = bookmarkGroupQueryReader;
        this.bookmarkCountQueryReader = bookmarkCountQueryReader;
        this.bookmarkListQueryReader = bookmarkListQueryReader;
    }

    public BookmarkListQuery getList(ApiUser user, @Nullable String groupName) {
        BookmarkGroupQuery group = bookmarkGroupQueryReader.read(user.getUserId(), groupName);
        int count = bookmarkCountQueryReader.count(group);
        List<BookmarkQuery> bookmarks = bookmarkListQueryReader.getList(group);
        return new BookmarkListQuery(
                bookmarks,
                count
        );
    }
}
