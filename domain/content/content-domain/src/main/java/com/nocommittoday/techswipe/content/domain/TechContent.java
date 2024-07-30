package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
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
}
