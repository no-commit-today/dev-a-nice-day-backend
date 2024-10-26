package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;

public class Bookmark {

    private final BookmarkId id;

    private final BookmarkGroupId groupId;

    private final TechContentId contentId;

    public Bookmark(BookmarkId id, BookmarkGroupId groupId, TechContentId contentId) {
        this.id = id;
        this.groupId = groupId;
        this.contentId = contentId;
    }

    public BookmarkId getId() {
        return id;
    }

    public BookmarkGroupId getGroupId() {
        return groupId;
    }

    public TechContentId getContentId() {
        return contentId;
    }
}
