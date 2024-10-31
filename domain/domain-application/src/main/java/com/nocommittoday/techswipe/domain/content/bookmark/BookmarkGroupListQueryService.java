package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkGroupListQueryService {

    private final BookmarkGroupListReader bookmarkGroupListReader;

    public BookmarkGroupListQueryService(BookmarkGroupListReader bookmarkGroupListReader) {
        this.bookmarkGroupListReader = bookmarkGroupListReader;
    }

    public List<BookmarkGroup> getList(ApiUser apiUser) {
        return bookmarkGroupListReader.getList(apiUser.getUserId());
    }
}
