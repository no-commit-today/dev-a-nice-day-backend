package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentQuery;

public class BookmarkQuery {

    private final BookmarkId id;

    private final BookmarkGroupQuery group;

    private final TechContentQuery content;

    public BookmarkQuery(BookmarkId id, BookmarkGroupQuery group, TechContentQuery content) {
        this.id = id;
        this.group = group;
        this.content = content;
    }

    public BookmarkId getId() {
        return id;
    }

    public BookmarkGroupQuery getGroup() {
        return group;
    }

    public TechContentQuery getContent() {
        return content;
    }
}
