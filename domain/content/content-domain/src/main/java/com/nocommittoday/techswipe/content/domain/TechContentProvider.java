package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@Getter
@RequiredArgsConstructor
public class TechContentProvider {

    @NonNull
    private final TechContentProviderId id;

    @NonNull
    private final TechContentProviderType type;

    @NonNull
    private final String title;

    @NonNull
    private final String url;

    @Nullable
    private final Image.ImageId iconId;

    public record TechContentProviderId(long value) { }
}
