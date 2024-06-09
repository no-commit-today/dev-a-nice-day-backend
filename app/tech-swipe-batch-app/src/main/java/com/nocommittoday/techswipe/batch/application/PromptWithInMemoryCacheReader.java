package com.nocommittoday.techswipe.batch.application;

import com.nocommittoday.techswipe.collection.application.port.out.PromptReaderPort;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PromptWithInMemoryCacheReader {

    private final PromptReaderPort promptReaderPort;

    private final Map<String, Prompt> cache = Collections.synchronizedMap(new HashMap<>());

    public Prompt get(final PromptType type, final TechContentProviderType providerType) {
        final String key = type.name() + "-" + providerType.name();
        cache.computeIfAbsent(key, k ->
                promptReaderPort.get(type, providerType)
        );
        return cache.get(key);
    }
}
