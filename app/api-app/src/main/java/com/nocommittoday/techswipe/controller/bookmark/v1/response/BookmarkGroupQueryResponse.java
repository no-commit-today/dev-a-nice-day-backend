package com.nocommittoday.techswipe.controller.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupQuery;

public record BookmarkGroupQueryResponse(
        String name
) {

    public static BookmarkGroupQueryResponse from(BookmarkGroupQuery bookmarkGroupQuery) {
        return new BookmarkGroupQueryResponse(bookmarkGroupQuery.getName());
    }
}
