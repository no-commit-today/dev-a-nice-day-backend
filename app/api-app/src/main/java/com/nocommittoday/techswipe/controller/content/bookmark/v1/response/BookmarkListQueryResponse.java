package com.nocommittoday.techswipe.controller.content.bookmark.v1.response;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkListQueryResult;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkQuery;
import com.nocommittoday.techswipe.domain.content.TechCategory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record BookmarkListQueryResponse(
        List<BookmarkedContent> content
) {

    public static BookmarkListQueryResponse from(BookmarkListQueryResult query) {
        return new BookmarkListQueryResponse(
                query.bookmarks().stream()
                        .map(BookmarkQuery::getContent)
                        .map(content -> new BookmarkedContent(
                                String.valueOf(content.getId().value()),
                                content.getTitle(),
                                content.getPublishedDate(),
                                content.getSummary().getContent(),
                                content.getImageUrl(),
                                content.getCategories(),
                                String.valueOf(content.getProvider().getId().value()),
                                content.getProvider().getTitle(),
                                content.getProvider().getUrl(),
                                content.getProvider().getIconUrl()
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
            @Nullable String providerIconUrl
    ) {
    }
}
