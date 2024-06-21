package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptRegister;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.PromptJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PromptAppender {

    private final PromptJpaRepository promptJpaRepository;

    @Transactional
    public Prompt.PromptId save(final PromptRegister promptRegister) {
        promptJpaRepository.findAllByTypeAndProviderTypeAndDeletedIsFalse(
                promptRegister.type(), promptRegister.providerType()
        ).forEach(PromptEntity::delete);

        return new Prompt.PromptId(promptJpaRepository.save(
                new PromptEntity(
                        null,
                        promptRegister.type(),
                        promptRegister.providerType(),
                        promptRegister.version(),
                        promptRegister.model(),
                        promptRegister.content()
                )
        ).getId());
    }
}
