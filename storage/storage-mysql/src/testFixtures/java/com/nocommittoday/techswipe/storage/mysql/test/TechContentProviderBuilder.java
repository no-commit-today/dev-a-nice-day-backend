package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import jakarta.annotation.Nullable;

public class TechContentProviderBuilder {

    @Nullable
    private Long id;
    @Nullable
    private TechContentProviderType type;
    @Nullable
    private String title;
    @Nullable
    private String url;
    @Nullable
    private ImageEntity icon;

    public TechContentProviderBuilder() {
    }

    public TechContentProviderBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TechContentProviderBuilder type(TechContentProviderType type) {
        this.type = type;
        return this;
    }

    public TechContentProviderBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TechContentProviderBuilder url(String url) {
        this.url = url;
        return this;
    }

    public TechContentProviderBuilder icon(ImageEntity icon) {
        this.icon = icon;
        return this;
    }

    public TechContentProviderEntity build() {
        fillRequiredFields();
        return new TechContentProviderEntity(
            id,
            type,
            title,
            url,
            icon
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }
        long fieldId = id;

        if (type == null) {
            type = TechContentProviderType.DOMESTIC_COMPANY_BLOG;
        }
        if (title == null) {
            title = "provider-title-" + fieldId;
        }
        if (url == null) {
            url = "provider-url-" + fieldId;
        }
    }

}
