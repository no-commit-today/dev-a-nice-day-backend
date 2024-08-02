package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.image.domain.ImageId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TechContent {

    private final TechContentId id;

    private final TechContentProvider provider;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    @Nullable
    private final ImageId imageId;

    private final String summary;

    private final List<TechCategory> categories;

    public TechContent(
            TechContentId id,
            TechContentProvider provider,
            @Nullable ImageId imageId,
            String url,
            String title,
            LocalDate publishedDate,
            String summary,
            List<TechCategory> categories
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

    public TechContent edit(TechContentCategoryEdit categoryEdit) {
        return new TechContent(
                id,
                provider,
                imageId,
                url,
                title,
                publishedDate,
                summary,
                categoryEdit.categories()
        );
    }

    public List<TechCategory> getCategories() {
        return categories;
    }

    public TechContentId getId() {
        return id;
    }

    @Nullable
    public ImageId getImageId() {
        return imageId;
    }

    public TechContentProvider getProvider() {
        return provider;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
