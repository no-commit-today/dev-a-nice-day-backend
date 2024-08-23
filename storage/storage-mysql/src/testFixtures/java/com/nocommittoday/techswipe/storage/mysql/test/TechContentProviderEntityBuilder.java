package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.test.LocalAutoIncrementIdUtils;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import jakarta.annotation.Nullable;

public class TechContentProviderEntityBuilder {

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

    public static TechContentProviderEntity create() {
        return create(false);
    }

    public static TechContentProviderEntity create(boolean withId) {
        return new TechContentProviderEntityBuilder().build(withId);
    }

    public TechContentProviderEntityBuilder() {
    }

    public TechContentProviderEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TechContentProviderEntityBuilder type(TechContentProviderType type) {
        this.type = type;
        return this;
    }

    public TechContentProviderEntityBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TechContentProviderEntityBuilder url(String url) {
        this.url = url;
        return this;
    }

    public TechContentProviderEntityBuilder icon(ImageEntity icon) {
        this.icon = icon;
        return this;
    }

    public TechContentProviderEntity build() {
        return build(false);
    }

    public TechContentProviderEntity build(boolean withId) {
        fillRequiredFields(withId);
        return new TechContentProviderEntity(
            id,
            type,
            title,
            url,
            icon
        );
    }

    private void fillRequiredFields(boolean withId) {
        if (withId && id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }
        long fieldId = id != null ? id : LocalAutoIncrementIdUtils.nextId();

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
