package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class CollectedContentCategorizeSkipListener
        implements SkipListener<CollectedContentEntity, CollectedContentEntity> {

    @Override
    public void onSkipInProcess(final CollectedContentEntity item, final Throwable t) {
        log.error("카테고리를 처리하는 동안 예외가 발생했습니다. CollectedContent.id={}.", item.getId(), t);
    }
}
