package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Prompt {

    @NonNull
    private final PromptId id;

    @NonNull
    private final PromptType type;

    @NonNull
    private final TechContentProviderType providerType;

    @NonNull
    private final String version;

    @NonNull
    private final String content;

    public record PromptId(long value) { }
}
