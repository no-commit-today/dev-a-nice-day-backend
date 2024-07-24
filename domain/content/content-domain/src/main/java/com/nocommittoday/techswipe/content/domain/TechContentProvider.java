package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class TechContentProvider {

    private final Id id;

    private final TechContentProviderType type;

    private final String title;

    private final String url;

    @Nullable
    private final ImageId iconId;

    public TechContentProvider(
            final Id id,
            final TechContentProviderType type,
            final String title,
            final String url,
            @Nullable final ImageId iconId
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.iconId = iconId;
    }

    public TechContentProvider editIcon(final ImageId iconId) {
        return new TechContentProvider(
                this.id,
                this.type,
                this.title,
                this.url,
                iconId
        );
    }

    public record Id(long value) { }
}
