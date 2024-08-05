package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategoryNotApplicableException;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentCategoryEdit;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentDeleter;
import com.nocommittoday.techswipe.domain.content.TechContentReader;
import com.nocommittoday.techswipe.domain.content.TechContentUpdater;
import com.nocommittoday.techswipe.domain.content.TechContentUrlExistsReader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ContentCategoryEditService {

    private final CollectedContentReader collectedContentReader;
    private final CollectedContentUpdater collectedContentUpdater;
    private final TechContentUrlExistsReader techContentUrlExistsReader;
    private final TechContentReader techContentReader;
    private final TechContentUpdater techContentUpdater;
    private final TechContentDeleter techContentDeleter;

    public ContentCategoryEditService(
            CollectedContentReader collectedContentReader,
            CollectedContentUpdater collectedContentUpdater,
            TechContentUrlExistsReader techContentUrlExistsReader,
            TechContentReader techContentReader,
            TechContentUpdater techContentUpdater,
            TechContentDeleter techContentDeleter
    ) {
        this.collectedContentReader = collectedContentReader;
        this.collectedContentUpdater = collectedContentUpdater;
        this.techContentUrlExistsReader = techContentUrlExistsReader;
        this.techContentReader = techContentReader;
        this.techContentUpdater = techContentUpdater;
        this.techContentDeleter = techContentDeleter;
    }

    public void editCategory(CollectedContentId id, ContentCategoryEdit categoryEdit) {
        CollectedContent oldCollectedContent = collectedContentReader.get(id);
        CollectedContent newCollectedContent = oldCollectedContent.editCategory(categoryEdit);

        collectedContentUpdater.update(newCollectedContent);
    }

    public void applyCategoryEdited(CollectedContentId id) {
        CollectedContent collectedContent = collectedContentReader.get(id);
        if (CollectionUtils.isEmpty(collectedContent.getCategories())) {
            throw new CollectionCategoryNotApplicableException(collectedContent.getId());
        }

        TechContentId techContentId = id.toTechContentId();
        if (!techContentUrlExistsReader.existsIncludingDeleted(techContentId)) {
            return;
        }

        TechContent techContent = techContentReader.getIncludingDeleted(techContentId);
        if (CollectionStatus.FILTERED == collectedContent.getStatus()) {
            techContentDeleter.delete(techContent.getId());
        } else {
            List<TechCategory> categories = collectedContent.getCategories().stream()
                    .map(CollectionCategory::getTechCategory)
                    .toList();
            techContentUpdater.update(techContent.edit(new TechContentCategoryEdit(categories)));
        }
    }
}
