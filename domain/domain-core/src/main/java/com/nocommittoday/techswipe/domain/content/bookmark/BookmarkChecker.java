package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookmarkChecker {

    private final Set<TechContentId> bookmarkedContentIds;

    private BookmarkChecker(Set<TechContentId> bookmarkedContentIds) {
        this.bookmarkedContentIds = bookmarkedContentIds;
    }

    public static BookmarkChecker create(List<Bookmark> bookmarks) {
        return new BookmarkChecker(
                bookmarks.stream()
                        .map(Bookmark::getContentId)
                        .collect(Collectors.toSet())
        );
    }

    public boolean check(TechContentId contentId) {
        return bookmarkedContentIds.contains(contentId);
    }
}
