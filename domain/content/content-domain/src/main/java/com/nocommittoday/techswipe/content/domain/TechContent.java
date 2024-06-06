package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@Getter
public class TechContent {

    @NonNull
    private final TechContentId id;

    @NonNull
    private final TechContentProvider provider;

    @NonNull
    private final String url;

    @NonNull
    private final String title;

    @Nullable
    private final Image.ImageId imageId;

    @NonNull
    private final String summary;

    @NonNull
    private final List<TechCategory> categories;

    public record TechContentId(long value) { }

    public TechContent(
            @NonNull final TechContentId id,
            @NonNull final TechContentProvider provider,
            @Nullable final Image.ImageId imageId,
            @NonNull final String url,
            @NonNull final String title,
            @NonNull final String summary,
            @NonNull final List<TechCategory> categories
    ) {
        this.id = id;
        this.provider = provider;
        this.imageId = imageId;
        this.url = url;
        this.title = title;
        this.summary = summary;
        this.categories = Collections.unmodifiableList(categories);
    }
}
