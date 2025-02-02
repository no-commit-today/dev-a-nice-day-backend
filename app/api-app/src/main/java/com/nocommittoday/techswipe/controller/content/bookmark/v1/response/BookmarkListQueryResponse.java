package com.nocommittoday.techswipe.controller.content.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkListQueryResult;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkQuery;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record BookmarkListQueryResponse(
        List<BookmarkedContent> content
) {

    public static BookmarkListQueryResponse from(BookmarkListQueryResult query) {
        return new BookmarkListQueryResponse(
                query.bookmarks().stream()
                        .map(bookmark -> new BookmarkedContent(
                                String.valueOf(bookmark.getContent().getId().value()),
                                bookmark.getContent().getTitle(),
                                bookmark.getContent().getPublishedDate(),
                                bookmark.getContent().getSummary().getContent(),
                                bookmark.getContent().getImageUrl(),
                                bookmark.getContent().getCategories(),
                                String.valueOf(bookmark.getContent().getProvider().getId().value()),
                                bookmark.getContent().getProvider().getTitle(),
                                bookmark.getContent().getProvider().getUrl(),
                                bookmark.getContent().getProvider().getIconUrl(),
                                bookmark.getGroup().getName()
                        )).toList()
        );
    }

    public static BookmarkListQueryResponse from(List<BookmarkQuery> content) {
        return new BookmarkListQueryResponse(
                content.stream()
                        .map(bookmark -> new BookmarkedContent(
                                String.valueOf(bookmark.getContent().getId().value()),
                                bookmark.getContent().getTitle(),
                                bookmark.getContent().getPublishedDate(),
                                bookmark.getContent().getSummary().getContent(),
                                bookmark.getContent().getImageUrl(),
                                bookmark.getContent().getCategories(),
                                String.valueOf(bookmark.getContent().getProvider().getId().value()),
                                bookmark.getContent().getProvider().getTitle(),
                                bookmark.getContent().getProvider().getUrl(),
                                bookmark.getContent().getProvider().getIconUrl(),
                                bookmark.getGroup().getName()
                        )).toList()
        );
    }

    public record BookmarkedContent(
            String id,
            String title,
            LocalDate publishedDate,
            String summary,
            @Nullable String imageUrl,
            List<TechCategory> categories,
            String providerId,
            String providerTitle,
            String providerUrl,
            @Nullable String providerIconUrl,
            String bookmarkGroupName
    ) {
    }
}
