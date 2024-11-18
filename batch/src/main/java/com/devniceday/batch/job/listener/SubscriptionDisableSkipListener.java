package com.devniceday.batch.job.listener;

import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class SubscriptionDisableSkipListener implements SkipListener<BatchSubscriptionEntity, Object> {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionDisableSkipListener.class);

    private final BatchSubscriptionEntityRepository repository;
    private final AlertManager alertManager;

    public SubscriptionDisableSkipListener(BatchSubscriptionEntityRepository repository, AlertManager alertManager) {
        this.repository = repository;
        this.alertManager = alertManager;
    }

    @Override
    public void onSkipInProcess(BatchSubscriptionEntity item, Throwable t) {
        log.error("컨텐츠 수집 중 오류 발생 subscriptionId={}", item.getId(), t);
        repository.disable(item);
        alertManager.alert(
                AlertCommand.builder()
                        .error()
                        .title("구독 처리 중 오류 발생")
                        .content(String.format("- subscriptionId: %d", item.getId()))
                        .ex((Exception) t)
                        .build()
        );
    }
}
