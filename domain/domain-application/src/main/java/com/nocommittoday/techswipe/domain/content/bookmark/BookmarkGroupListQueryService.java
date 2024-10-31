package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkGroupListQueryService {

    private final BookmarkGroupListReader bookmarkGroupListReader;
    private final BookmarkGroupWithContainsListReader bookmarkGroupWithContainsListReader;

    public BookmarkGroupListQueryService(
            BookmarkGroupListReader bookmarkGroupListReader,
            BookmarkGroupWithContainsListReader bookmarkGroupWithContainsListReader
    ) {
        this.bookmarkGroupListReader = bookmarkGroupListReader;
        this.bookmarkGroupWithContainsListReader = bookmarkGroupWithContainsListReader;
    }

    public List<BookmarkGroup> getList(ApiUser apiUser) {
        return bookmarkGroupListReader.getList(apiUser.getUserId());
    }

    public List<BookmarkGroupWithContains> getListWithContains(ApiUser apiUser, TechContentId contentId) {
        List<BookmarkGroup> groups = bookmarkGroupListReader.getList(apiUser.getUserId());
        return bookmarkGroupWithContainsListReader.getList(groups, contentId);
    }
}
