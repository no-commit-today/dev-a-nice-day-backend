package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class CollectedContentPublishJobSkipListener
        implements SkipListener<CollectedContentEntity, Pair<CollectedContentEntity, TechContentEntity>> {

    @Override
    public void onSkipInProcess(CollectedContentEntity item, Throwable t) {
        log.error("컨텐츠 발행 중 오류 발생 collectedContentId={}", item.getId(), t);
    }
}
