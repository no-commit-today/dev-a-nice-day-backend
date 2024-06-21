package com.nocommittoday.techswipe.batch.application;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.collection.infrastructure.PromptReader;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PromptWithInMemoryCacheReader {

    private final PromptReader promptReader;

    private final Map<String, Prompt> cache = Collections.synchronizedMap(new HashMap<>());

    public Prompt get(final PromptType type, final TechContentProviderType providerType) {
        final String key = type.name() + "-" + providerType.name();
        cache.computeIfAbsent(key, k -> {
                    final Prompt prompt = promptReader.get(type, providerType);
                    log.debug("프롬프트 캐싱 type: {}, providerType: {}", type, providerType);
                    return prompt;
                }
        );
        return cache.get(key);
    }
}
