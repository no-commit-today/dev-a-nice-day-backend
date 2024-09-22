package com.nocommittoday.techswipe.domain.bookmark;

import java.util.List;

public class BookmarkListQuery {

    private final List<BookmarkQuery> content;
    private final int count;

    public BookmarkListQuery(List<BookmarkQuery> content, int count) {
        this.content = content;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public List<BookmarkQuery> getContent() {
        return content;
    }
}
