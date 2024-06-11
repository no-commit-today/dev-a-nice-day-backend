package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.application.port.out.PromptReaderPort;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionPromptNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptJpaRepository;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PromptReaderAdapter implements PromptReaderPort {

    private final PromptJpaRepository promptJpaRepository;

    @Override
    public Prompt get(final PromptType type, final TechContentProviderType providerType) {
        return promptJpaRepository.findByTypeAndProviderType(type, providerType)
                .filter(PromptEntity::isUsed)
                .map(PromptEntity::toDomain)
                .orElseThrow(() -> new CollectionPromptNotFoundException(type, providerType));
    }
}
