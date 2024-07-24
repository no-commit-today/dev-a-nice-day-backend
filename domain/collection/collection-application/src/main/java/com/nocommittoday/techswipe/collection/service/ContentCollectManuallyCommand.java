package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentCollectManuallyCommand(
        @NonNull TechContentProvider.Id providerId,
        @NonNull String url,
        @NonNull String title,
        @NonNull LocalDate publishedDate,
        @NonNull String content,
        @Nullable String imageUrl
) {

    public ContentCollect toDomain(final CollectedContent.Id id) {
        return new ContentCollect(
                id,
                providerId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }
}
