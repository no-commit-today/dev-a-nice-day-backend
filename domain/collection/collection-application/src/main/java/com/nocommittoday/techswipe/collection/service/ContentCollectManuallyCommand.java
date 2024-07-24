package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentCollectManuallyCommand(
        @NonNull TechContentProviderId providerId,
        @NonNull String url,
        @NonNull String title,
        @NonNull LocalDate publishedDate,
        @NonNull String content,
        @Nullable String imageUrl
) {

    public ContentCollect toDomain(final CollectedContentId id) {
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
