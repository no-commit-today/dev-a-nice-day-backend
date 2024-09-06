package com.nocommittoday.techswipe.domain.content;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public class TechContentQuery {

    private final TechContentId id;

    private final TechContentProviderQuery provider;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    @Nullable
    private final String imageUrl;

    private final Summary summary;

    private final List<TechCategory> categories;

    public TechContentQuery(
            TechContentId id,
            TechContentProviderQuery provider,
            @Nullable String imageUrl,
            String url,
            String title,
            LocalDate publishedDate,
            Summary summary,
            List<TechCategory> categories
    ) {
        this.id = id;
        this.provider = provider;
        this.imageUrl = imageUrl;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.categories = categories;
    }

    public TechContentId getId() {
        return id;
    }

    public TechContentProviderQuery getProvider() {
        return provider;
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

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }
}
