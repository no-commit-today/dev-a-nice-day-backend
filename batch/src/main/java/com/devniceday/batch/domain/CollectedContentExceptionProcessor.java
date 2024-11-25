package com.devniceday.batch.domain;

import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchCollectedContentEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CollectedContentExceptionProcessor {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentExceptionProcessor.class);

    private final BatchCollectedContentEntityRepository repository;
    private final AlertManager alertManager;

    public CollectedContentExceptionProcessor(
            BatchCollectedContentEntityRepository repository, AlertManager alertManager) {
        this.repository = repository;
        this.alertManager = alertManager;
    }

    public void process(long collectedContentId, Exception ex) {
        log.error("컨텐츠 수집 중 오류 발생하여 filter 처리 합니다. collectedContentId={}", collectedContentId, ex);
        repository.filter(collectedContentId);
        alertManager.alert(
                AlertCommand.builder()
                        .error()
                        .title("컨텐츠 수집 중 오류 발생")
                        .content(String.format("- collectedContentId: %d", collectedContentId))
                        .ex(ex)
                        .build()
        );
    }
}
