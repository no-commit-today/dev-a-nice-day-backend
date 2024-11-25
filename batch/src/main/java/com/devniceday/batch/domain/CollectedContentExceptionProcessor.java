package com.devniceday.batch.domain;

import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchCollectedContentEntityRepository;
import org.springframework.stereotype.Component;

@Component
public class CollectedContentExceptionProcessor {

    private final BatchCollectedContentEntityRepository repository;
    private final AlertManager alertManager;

    public CollectedContentExceptionProcessor(
            BatchCollectedContentEntityRepository repository, AlertManager alertManager) {
        this.repository = repository;
        this.alertManager = alertManager;
    }

    public void process(long collectedContentId, Exception ex) {
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
