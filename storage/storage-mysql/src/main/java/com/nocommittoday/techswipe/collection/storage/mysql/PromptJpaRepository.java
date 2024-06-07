package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptJpaRepository extends JpaRepository<PromptEntity, Long> {

    Optional<PromptEntity> findByTypeAndProviderType(PromptType type, TechContentProviderType providerType);
}
