package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.infrastructure.alert.AlertCommand;
import com.nocommittoday.techswipe.infrastructure.alert.AlertManager;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class ContentCollectJobSkipListener implements SkipListener<SubscriptionEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(ContentCollectJobSkipListener.class);

    private final AlertManager alertManager;

    public ContentCollectJobSkipListener(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    @Override
    public void onSkipInProcess(SubscriptionEntity item, Throwable t) {
        log.error("컨텐츠 수집 중 오류 발생 subscriptionId={}", item.getId(), t);
        alertManager.alert(
                AlertCommand.builder()
                        .error()
                        .title("ContentCollectJob 중 오류 발생")
                        .content(String.format("- subscriptionId: %d", item.getId()))
                        .ex((Exception) t)
                        .build()
        );
    }
}
