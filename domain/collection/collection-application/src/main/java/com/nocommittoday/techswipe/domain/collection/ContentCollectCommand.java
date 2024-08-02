package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentCollectCommand(
        TechContentProviderId providerId,
        String url,
        String title,
        LocalDate publishedDate,
        String content,
        @Nullable String imageUrl
) {

    public ContentCollect toDomain(CollectedContentId id) {
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
