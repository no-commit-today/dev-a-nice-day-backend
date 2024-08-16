package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.ContentCollect;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record AdminContentCollectCommand(
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
