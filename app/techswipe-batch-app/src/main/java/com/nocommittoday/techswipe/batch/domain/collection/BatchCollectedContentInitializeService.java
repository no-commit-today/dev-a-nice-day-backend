package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.batch.domain.subscription.BatchSubscribedContentInitializerDelegator;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.infrastructure.web.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BatchCollectedContentInitializeService {

    private static final Logger log = LoggerFactory.getLogger(BatchCollectedContentInitializeService.class);

    private final BatchSubscribedContentInitializerDelegator contentInitializer;

    public BatchCollectedContentInitializeService(BatchSubscribedContentInitializerDelegator contentInitializer) {
        this.contentInitializer = contentInitializer;
    }

    public CollectedContent initialize(Subscription subscription, CollectedContent content) {
        SubscribedContent subscribedContent = new SubscribedContent(
                subscription.getId(),
                content.getUrl(),
                false,
                content.getTitle(),
                content.getImageUrl(),
                content.getPublishedDate(),
                content.getContent()
        );

        try {
            SubscribedContent initializedSubscribedContent = contentInitializer
                    .initialize(subscription, subscribedContent);
            return content.initialize(
                    initializedSubscribedContent.getTitle(),
                    initializedSubscribedContent.getPublishedDate(),
                    initializedSubscribedContent.getContent(),
                    initializedSubscribedContent.getImageUrl()
            );
        } catch (ClientException.NotFound ex) {
            log.warn("CollectedContent.id={} 의 url 이 존재하지 않아서 필터링되었습니다.", content.getId());
            return content.filtered();
        }
    }
}
