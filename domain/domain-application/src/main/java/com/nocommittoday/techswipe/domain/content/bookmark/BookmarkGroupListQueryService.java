package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkGroupListQueryService {

    private final BookmarkGroupListQueryReader bookmarkGroupListQueryReader;

    public BookmarkGroupListQueryService(BookmarkGroupListQueryReader bookmarkGroupListQueryReader) {
        this.bookmarkGroupListQueryReader = bookmarkGroupListQueryReader;
    }

    public List<BookmarkGroupQuery> getList(ApiUser apiUser) {
        return bookmarkGroupListQueryReader.getList(apiUser.getUserId());
    }
}
