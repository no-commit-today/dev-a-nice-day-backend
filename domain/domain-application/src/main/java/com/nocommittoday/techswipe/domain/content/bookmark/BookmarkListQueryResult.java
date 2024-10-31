package com.nocommittoday.techswipe.domain.content.bookmark;

import java.util.List;

public record BookmarkListQueryResult(
        List<BookmarkQuery> bookmarks
) {
}
