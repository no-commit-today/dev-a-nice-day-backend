package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.test.LocalAutoIncrementIdUtils;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public class TechContentEntityBuilder {

    @Nullable
    private Long id;
    @Nullable
    private TechContentProviderEntity provider;
    @Nullable
    private ImageEntity image;
    @Nullable
    private String url;
    @Nullable
    private String title;
    @Nullable
    private String summary;
    @Nullable
    private LocalDate publishedDate;
    @Nullable
    private List<TechCategory> categories;

    public static TechContentEntity create() {
        return new TechContentEntityBuilder().build();
    }

    public TechContentEntityBuilder() {
    }

    public TechContentEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TechContentEntityBuilder provider(TechContentProviderEntity provider) {
        this.provider = provider;
        return this;
    }

    public TechContentEntityBuilder image(ImageEntity image) {
        this.image = image;
        return this;
    }

    public TechContentEntityBuilder url(String url) {
        this.url = url;
        return this;
    }

    public TechContentEntityBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TechContentEntityBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public TechContentEntityBuilder publishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public TechContentEntityBuilder categories(TechCategory... categories) {
        return categories(List.of(categories));
    }

    public TechContentEntityBuilder categories(List<TechCategory> categories) {
        this.categories = categories;
        return this;
    }

    public TechContentEntity build() {
        fillRequiredFields();
        TechContentEntity entity = new TechContentEntity(
                id,
                provider,
                image != null ? image : null,
                url,
                title,
                summary,
                publishedDate
        );
        categories.forEach(entity::addCategory);
        return entity;
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }

        if (provider == null) {
            provider = TechContentProviderEntityBuilder.create();
        }
        if (url == null) {
            url = "content-url-" + id;
        }
        if (title == null) {
            title = "content-title-" + id;
        }
        if (summary == null) {
            summary = "content-summary-" + id;
        }
        if (publishedDate == null) {
            publishedDate = LocalDate.of(2021, 1, 1);
        }
        if (categories == null) {
            categories = List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING);
        }
    }
}
