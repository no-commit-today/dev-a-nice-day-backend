package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechContentProvider {

    private final Id id;

    private final TechContentProviderType type;

    private final String title;

    private final String url;

    private final Image.ImageId iconId;

    public record Id(long value) { }
}
