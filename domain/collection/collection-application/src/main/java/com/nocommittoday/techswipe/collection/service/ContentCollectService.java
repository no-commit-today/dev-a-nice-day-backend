package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ContentCollectService {

    private final CollectedContentAppender collectedContentAppender;

    public void collect(final @NonNull ContentCollect command) {
        collectedContentAppender.save(command.toDomain());
    }
}
