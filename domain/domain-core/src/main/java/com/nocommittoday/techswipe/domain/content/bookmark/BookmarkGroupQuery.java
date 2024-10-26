package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.UserId;

import javax.annotation.Nullable;

public class BookmarkGroupQuery {

    @Nullable
    private final BookmarkGroupId id;

    private final UserId userId;

    private final String name;

    public BookmarkGroupQuery(@Nullable BookmarkGroupId id, UserId userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public static BookmarkGroupQuery allGroup(UserId userId) {
        return new BookmarkGroupQuery(null, userId, BookmarkGroupConst.ALL_GROUP_NAME);
    }

    @Nullable
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
