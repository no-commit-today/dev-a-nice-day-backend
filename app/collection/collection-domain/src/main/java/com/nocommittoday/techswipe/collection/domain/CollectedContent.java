package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CollectedContent {

    @NonNull
    private final CollectedContentId id;

    @NonNull
    private final CollectionType type;

    @NonNull
    private final TechContentProvider.TechContentProviderId providerId;

    @NonNull
    private final String url;

    @NonNull
    private final String title;

    @NonNull
    private final LocalDate publishedDate;

    @NonNull
    private final String content;

    @Nullable
    private final String imageUrl;

    @Nullable
    private final List<TechCategory> categories;

    public record CollectedContentId(long id) { }
}
