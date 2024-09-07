package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import com.nocommittoday.techswipe.infrastructure.alert.AlertCommand;
import com.nocommittoday.techswipe.infrastructure.alert.AlertManager;
import com.nocommittoday.techswipe.infrastructure.openai.collection.CategorizationProcessor;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

public class CollectedContentCategorizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentCategorizeProcessor.class);

    private final CategorizationProcessor categorizationProcessor;
    private final AlertManager alertManager;

    public CollectedContentCategorizeProcessor(
            CategorizationProcessor categorizationProcessor,
            AlertManager alertManager
    ) {
        this.categorizationProcessor = categorizationProcessor;
        this.alertManager = alertManager;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContentEntityEditor editor = item.toEditor();
        CollectedContent collectedContent = item.toDomain();

        try {
            CollectionCategoryList categoryList = categorizationProcessor.categorize(collectedContent);
            CollectedContent categorized = collectedContent.categorize(categoryList);
            editor.setCategories(Objects.requireNonNull(categorized.getCategoryList()).getContent());
            editor.setStatus(categorized.getStatus());
        } catch (DomainValidationException e) {
            log.error("CollectedContent.id={} 의 분류에 실패했습니다.", item.getId(), e);
            CollectedContent categorizationFailed = collectedContent.failCategorization();
            editor.setStatus(categorizationFailed.getStatus());
            alertManager.alert(
                    AlertCommand.builder()
                            .error()
                            .title("CollectedContentCategorizeJob CollectedContent 분류 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }

        item.edit(editor);
        return item;
    }
}
