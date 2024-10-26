package com.nocommittoday.techswipe.controller.content.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupQuery;

public record BookmarkGroupQueryResponse(
        String name
) {

    public static BookmarkGroupQueryResponse from(BookmarkGroupQuery bookmarkGroupQuery) {
        return new BookmarkGroupQueryResponse(bookmarkGroupQuery.getName());
    }
}
