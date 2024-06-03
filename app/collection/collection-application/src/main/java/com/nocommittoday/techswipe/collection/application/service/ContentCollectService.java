package com.nocommittoday.techswipe.collection.application.service;

import com.nocommittoday.techswipe.collection.application.port.in.ContentCollectUseCase;
import com.nocommittoday.techswipe.collection.application.port.out.CollectedContentSavePort;
import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ContentCollectService implements ContentCollectUseCase {

    private final CollectedContentSavePort collectedContentSavePort;

    @Override
    public void collect(final @NonNull ContentCollect command) {
        collectedContentSavePort.save(command);
    }
}
