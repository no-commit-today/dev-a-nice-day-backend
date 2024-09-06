package com.nocommittoday.techswipe.domain.content;

import javax.annotation.Nullable;

public class TechContentProviderQuery {

    private final TechContentProviderId id;

    private final TechContentProviderType type;

    private final String title;

    private final String url;

    @Nullable
    private final String iconUrl;


    public TechContentProviderQuery(
            TechContentProviderId id,
            TechContentProviderType type,
            String title,
            String url,
            @Nullable String iconUrl
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.iconUrl = iconUrl;
    }

    public TechContentProviderId getId() {
        return id;
    }

    public TechContentProviderType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getIconUrl() {
        return iconUrl;
    }
}
