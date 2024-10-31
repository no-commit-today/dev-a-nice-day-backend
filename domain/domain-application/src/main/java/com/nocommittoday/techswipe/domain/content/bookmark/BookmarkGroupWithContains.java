package com.nocommittoday.techswipe.domain.content.bookmark;

public record BookmarkGroupWithContains(
        BookmarkGroup group,
        boolean contains
) {
}
