package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.admin.domain.exception.AdminCollectionCategoryEditFailureException;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminCollectedContentEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminTechContentEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminTechContentProviderEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminContentCategoryEditService {

    private final AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository;
    private final AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository;
    private final AdminTechContentEntityJpaRepository adminTechContentEntityJpaRepository;

    public AdminContentCategoryEditService(
            AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository,
            AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository,
            AdminTechContentEntityJpaRepository adminTechContentEntityJpaRepository
    ) {
        this.adminCollectedContentEntityJpaRepository = adminCollectedContentEntityJpaRepository;
        this.adminTechContentProviderEntityJpaRepository = adminTechContentProviderEntityJpaRepository;
        this.adminTechContentEntityJpaRepository = adminTechContentEntityJpaRepository;
    }

    @Transactional
    public void edit(CollectedContentId id, CollectionCategoryList categoryList) {
        CollectedContentEntity collectedContentEntity = adminCollectedContentEntityJpaRepository.findById(id.value())
                .orElseThrow(() -> new CollectionNotFoundException(id));
        CollectionStatus nextStatus = getNextStatus(collectedContentEntity.getStatus(), categoryList);
        adminCollectedContentEntityJpaRepository.updateStatusAndCategoriesById(id.value(), nextStatus, categoryList.getCategories());

        TechContentId techContentId = id.toTechContentId();
        if (CollectionStatus.FILTERED == nextStatus) {
            adminTechContentEntityJpaRepository.findById(techContentId.value())
                    .ifPresent(TechContentEntity::delete);
        } else if (adminTechContentEntityJpaRepository.existsById(techContentId.value())) {
            adminTechContentEntityJpaRepository.updateCategoriesById(techContentId.value(), categoryList.toTechCategories());
        }

    }

    private CollectionStatus getNextStatus(CollectionStatus status, CollectionCategoryList categoryList) {

        if (CollectionStatus.FILTERED == status) {
            return categoryList.containsUnused() ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;
        } else if (CollectionStatus.CATEGORIZED == status) {
            return categoryList.containsUnused() ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;
        } else if (CollectionStatus.SUMMARIZED == status) {
            return categoryList.containsUnused() ? CollectionStatus.FILTERED : CollectionStatus.SUMMARIZED;
        } else if (CollectionStatus.PUBLISHED == status) {
            return categoryList.containsUnused() ? CollectionStatus.FILTERED : CollectionStatus.PUBLISHED;
        }

        throw new AdminCollectionCategoryEditFailureException(status);
    }
}
