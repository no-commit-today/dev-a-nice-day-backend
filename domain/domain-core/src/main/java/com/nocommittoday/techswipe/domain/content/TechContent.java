package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TechContent {

    private final TechContentId id;

    private final TechContentProviderId providerId;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    @Nullable
    private final ImageId imageId;

    private final Summary summary;

    private final List<TechCategory> categories;

    public TechContent(
            TechContentId id,
            TechContentProviderId providerId,
            @Nullable ImageId imageId,
            String url,
            String title,
            LocalDate publishedDate,
            Summary summary,
            List<TechCategory> categories
    ) {
        this.id = id;
        this.providerId = providerId;
        this.imageId = imageId;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.categories = Collections.unmodifiableList(categories);
    }

    public TechContentId getId() {
        return id;
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }

    @Nullable
    public ImageId getImageId() {
        return imageId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }
}
