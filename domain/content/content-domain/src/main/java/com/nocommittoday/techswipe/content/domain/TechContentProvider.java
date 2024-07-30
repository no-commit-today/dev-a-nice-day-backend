package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class TechContentProvider {

    private final TechContentProviderId id;

    private final TechContentProviderType type;

    private final String title;

    private final String url;

    @Nullable
    private final ImageId iconId;

    public TechContentProvider(
            TechContentProviderId id,
            TechContentProviderType type,
            String title,
            String url,
            @Nullable ImageId iconId
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.iconId = iconId;
    }

    public TechContentProvider editIcon(ImageId iconId) {
        return new TechContentProvider(
                this.id,
                this.type,
                this.title,
                this.url,
                iconId
        );
    }

}
