package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.UserId;

public class BookmarkGroup {

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
