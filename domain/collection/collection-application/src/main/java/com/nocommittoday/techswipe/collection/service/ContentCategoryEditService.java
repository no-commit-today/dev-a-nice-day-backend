package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.ContentCategoryEdit;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionCategoryNotApplicableException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentReader;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUpdater;
import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentCategoryEdit;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.infrastructure.TechContentDeleter;
import com.nocommittoday.techswipe.content.infrastructure.TechContentReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUpdater;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUrlExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentCategoryEditService {

    private final CollectedContentReader collectedContentReader;
    private final CollectedContentUpdater collectedContentUpdater;
    private final TechContentUrlExistsReader techContentUrlExistsReader;
    private final TechContentReader techContentReader;
    private final TechContentUpdater techContentUpdater;
    private final TechContentDeleter techContentDeleter;

    public void editCategory(final CollectedContentId id, final ContentCategoryEdit categoryEdit) {
        final CollectedContent oldCollectedContent = collectedContentReader.get(id);
        final CollectedContent newCollectedContent = oldCollectedContent.editCategory(categoryEdit);

        collectedContentUpdater.update(newCollectedContent);
    }

    public void applyCategoryEdited(final CollectedContentId id) {
        final CollectedContent collectedContent = collectedContentReader.get(id);
        if (CollectionUtils.isEmpty(collectedContent.getCategories())) {
            throw new CollectionCategoryNotApplicableException(collectedContent.getId());
        }

        final TechContentId techContentId = id.toTechContentId();
        if (!techContentUrlExistsReader.existsIncludingDeleted(techContentId)) {
            return;
        }

        final TechContent techContent = techContentReader.getIncludingDeleted(techContentId);
        if (CollectionStatus.FILTERED == collectedContent.getStatus()) {
            techContentDeleter.delete(techContent.getId());
        } else {
            final List<TechCategory> categories = collectedContent.getCategories().stream()
                    .map(CollectionCategory::getTechCategory)
                    .toList();
            techContentUpdater.update(techContent.edit(new TechContentCategoryEdit(categories)));
        }
    }
}
