package com.devniceday.batch.domain;

import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchSubscriptionEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionExceptionProcessor {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionExceptionProcessor.class);

    private final BatchSubscriptionEntityRepository repository;
    private final AlertManager alertManager;

    public SubscriptionExceptionProcessor(BatchSubscriptionEntityRepository repository, AlertManager alertManager) {
        this.repository = repository;
        this.alertManager = alertManager;
    }

    public void process(long subscriptionId, Exception ex) {
        log.error("컨텐츠 수집 중 오류 발생하여 disable 처리 합니다. subscriptionId={}", subscriptionId, ex);
        repository.disable(subscriptionId);
        alertManager.alert(
                AlertCommand.builder()
                        .error()
                        .title("구독 처리 중 오류 발생")
                        .content(String.format("- subscriptionId: %d", subscriptionId))
                        .ex(ex)
                        .build()
        );
    }
}
