package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.image.domain.Image;
import lombok.NonNull;

import javax.annotation.Nullable;

public record ProviderResult(
        @NonNull TechContentProvider.TechContentProviderId id,
        @NonNull String title,
        @NonNull String url,
        @Nullable Image.ImageId iconId
) {

    public static ProviderResult from(final TechContentProvider provider) {
        return new ProviderResult(
                provider.getId(),
                provider.getTitle(),
                provider.getUrl(),
                provider.getIconId()
        );
    }
}
