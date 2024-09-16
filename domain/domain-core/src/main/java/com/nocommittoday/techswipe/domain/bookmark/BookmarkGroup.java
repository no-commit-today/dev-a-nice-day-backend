package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.user.UserId;

public class BookmarkGroup {

    public static final String ALL_BOOKMARKS_GROUP_NAME = "모든 게시물";

    private final BookmarkGroupId id;

    private final UserId userId;

    private final String name;

    public BookmarkGroup(BookmarkGroupId id, UserId userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public BookmarkGroupId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
