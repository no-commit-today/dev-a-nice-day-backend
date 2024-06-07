package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Prompt {

    private final PromptId id;

    private final PromptType type;

    private final TechContentProviderType providerType;

    private final String version;

    private final String content;

    public record PromptId(long value) { }
}
