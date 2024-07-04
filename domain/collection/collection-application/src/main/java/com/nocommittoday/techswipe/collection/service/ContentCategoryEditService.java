package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.ContentCategoryEdit;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentReader;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUpdater;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.infrastructure.TechContentDeleter;
import com.nocommittoday.techswipe.content.infrastructure.TechContentReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUpdater;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUrlExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentCategoryEditService {

    private final CollectedContentReader collectedContentReader;
    private final CollectedContentUpdater collectedContentUpdater;
    private final TechContentUrlExistsReader techContentUrlExistsReader;
    private final TechContentReader techContentReader;
    private final TechContentUpdater techContentUpdater;
    private final TechContentDeleter techContentDeleter;

    public void editCategory(final CollectedContent.Id id, final ContentCategoryEdit categoryEdit) {
        final CollectedContent oldCollectedContent = collectedContentReader.get(id);
        final CollectedContent newCollectedContent = oldCollectedContent.editCategory(categoryEdit);

        collectedContentUpdater.update(newCollectedContent);
        if (!techContentUrlExistsReader.exists(newCollectedContent.getUrl())) {
            return ;
        }

        final TechContent techContent = techContentReader.getByUrl(newCollectedContent.getUrl());
        if (CollectionStatus.FILTERED == newCollectedContent.getStatus()) {
            techContentDeleter.delete(techContent.getId());
        } else {
            techContentUpdater.update(techContent.edit(categoryEdit.toTechContentCategoryEdit()));
        }
    }
}
