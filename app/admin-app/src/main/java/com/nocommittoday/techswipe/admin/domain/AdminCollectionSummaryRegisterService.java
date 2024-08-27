package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.admin.domain.exception.AdminCollectionSummaryNotRegistrableException;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.SummarizationPrompt;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminCollectedContentEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminCollectionSummaryRegisterService {

    private final CollectedContentJpaRepository collectedContentJpaRepository;
    private final AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository;

    public AdminCollectionSummaryRegisterService(
            CollectedContentJpaRepository collectedContentJpaRepository,
            AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository
    ) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
        this.adminCollectedContentEntityJpaRepository = adminCollectedContentEntityJpaRepository;
    }

    public SummarizationPrompt getPrompt(CollectedContentId id) {
        CollectedContent collectedContent = collectedContentJpaRepository.findById(id.value())
                .filter(CollectedContentEntity::isUsed)
                .map(CollectedContentEntity::toDomain)
                .orElseThrow(() -> new CollectionNotFoundException(id));
        return SummarizationPrompt.of(collectedContent);
    }

    @Transactional
    public void register(CollectedContentId id, Summary summary) {
        if (summary.isValid()) {
            throw new AdminCollectionSummaryNotRegistrableException(id, summary);
        }
        if (adminCollectedContentEntityJpaRepository.updateSummaryById(id.value(), summary.getContent())) {
            throw new CollectionNotFoundException(id);
        }
    }
}
