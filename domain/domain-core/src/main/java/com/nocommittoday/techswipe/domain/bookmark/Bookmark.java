package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.UserId;

public class Bookmark {

    private final BookmarkId id;

    private final BookmarkGroupId groupId;

    private final UserId userId;

    private final TechContentId contentId;

    public Bookmark(BookmarkId id, BookmarkGroupId groupId, UserId userId, TechContentId contentId) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.contentId = contentId;
    }

    public BookmarkId getId() {
        return id;
    }

    public BookmarkGroupId getGroupId() {
        return groupId;
    }

    public UserId getUserId() {
        return userId;
    }

    public TechContentId getContentId() {
        return contentId;
    }
}
