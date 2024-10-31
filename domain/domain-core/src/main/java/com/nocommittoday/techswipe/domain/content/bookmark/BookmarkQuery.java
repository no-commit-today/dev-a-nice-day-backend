package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentQuery;

public class BookmarkQuery {

    private final BookmarkId id;

    private final BookmarkGroup group;

    private final TechContentQuery content;

    public BookmarkQuery(BookmarkId id, BookmarkGroup group, TechContentQuery content) {
        this.id = id;
        this.group = group;
        this.content = content;
    }

    public BookmarkId getId() {
        return id;
    }

    public BookmarkGroup getGroup() {
        return group;
    }

    public TechContentQuery getContent() {
        return content;
    }
}
