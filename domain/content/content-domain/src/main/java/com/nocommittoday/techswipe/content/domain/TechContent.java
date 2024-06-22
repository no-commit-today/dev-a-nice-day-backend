package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
public class TechContent {

    private final Id id;

    private final TechContentProvider provider;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    @Nullable
    private final Image.ImageId imageId;

    private final String summary;

    private final List<TechCategory> categories;

    public record Id(long value) { }

    public TechContent(
            final Id id,
            final TechContentProvider provider,
            @Nullable final Image.ImageId imageId,
            final String url,
            final String title,
            final LocalDate publishedDate,
            final String summary,
            final List<TechCategory> categories
    ) {
        this.id = id;
        this.provider = provider;
        this.imageId = imageId;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.categories = Collections.unmodifiableList(categories);
    }
}
