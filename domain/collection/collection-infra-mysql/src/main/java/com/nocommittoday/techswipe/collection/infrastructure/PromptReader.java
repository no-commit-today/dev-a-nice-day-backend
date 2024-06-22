package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectionPromptNotFoundException;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptJpaRepository;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;

@Repository
@RequiredArgsConstructor
public class PromptReader {

    private final PromptJpaRepository promptJpaRepository;

    public Prompt get(final PromptType type, final TechContentProviderType providerType) {
        return promptJpaRepository.findAllByTypeAndProviderTypeAndDeletedIsFalse(type, providerType)
                .stream()
                .filter(PromptEntity::isUsed)
                .max(Comparator.comparing(PromptEntity::getCreatedAt))
                .map(PromptEntity::toDomain)
                .orElseThrow(() -> new CollectionPromptNotFoundException(type, providerType));
    }
}
