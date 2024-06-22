package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Prompt {

    private final Id id;

    private final PromptType type;

    private final TechContentProviderType providerType;

    private final String version;

    private final String model;

    private final String content;

    public record Id(long value) { }
}
