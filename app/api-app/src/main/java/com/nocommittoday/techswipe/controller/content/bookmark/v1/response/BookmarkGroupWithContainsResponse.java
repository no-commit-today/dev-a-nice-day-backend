package com.nocommittoday.techswipe.controller.content.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupWithContains;

public record BookmarkGroupWithContainsResponse(
        String name,
        boolean contains
) {

    public static BookmarkGroupWithContainsResponse from(BookmarkGroupWithContains group) {
        return new BookmarkGroupWithContainsResponse(group.group().getName(), group.contains());
    }
}
