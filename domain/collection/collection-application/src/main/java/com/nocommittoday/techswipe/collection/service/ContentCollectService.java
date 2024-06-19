package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ContentCollectService {

    private final CollectedContentAppender collectedContentAppender;

    public void collect(final ContentCollect command) {
        collectedContentAppender.save(command);
    }
}
