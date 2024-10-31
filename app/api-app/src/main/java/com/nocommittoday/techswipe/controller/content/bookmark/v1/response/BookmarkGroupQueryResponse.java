package com.nocommittoday.techswipe.controller.content.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroup;

public record BookmarkGroupQueryResponse(
        String name
) {

    public static BookmarkGroupQueryResponse from(BookmarkGroup bookmarkGroup) {
        return new BookmarkGroupQueryResponse(bookmarkGroup.getName());
    }
}
