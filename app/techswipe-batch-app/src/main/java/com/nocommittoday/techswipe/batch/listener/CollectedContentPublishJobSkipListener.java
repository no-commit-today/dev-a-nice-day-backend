package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class CollectedContentPublishJobSkipListener
        implements SkipListener<CollectedContentEntity, Pair<CollectedContentEntity, TechContentEntity>> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentPublishJobSkipListener.class);

    @Override
    public void onSkipInProcess(CollectedContentEntity item, Throwable t) {
        log.error("컨텐츠 발행 중 오류 발생 collectedContentId={}", item.getId(), t);
    }
}
