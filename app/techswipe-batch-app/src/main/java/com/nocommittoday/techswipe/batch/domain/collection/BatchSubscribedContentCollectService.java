package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.batch.domain.subscription.BatchSubscribedContentReaderDelegator;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchSubscribedContentCollectService {

    private static final Logger log = LoggerFactory.getLogger(BatchSubscribedContentCollectService.class);

    private static final int MAX_PAGE = 10;

    private final BatchSubscribedContentReaderDelegator contentReader;
    private final BatchCollectedSubscribedContentFilterCreator filterCreator;

    public BatchSubscribedContentCollectService(
            BatchSubscribedContentReaderDelegator contentReader,
            BatchCollectedSubscribedContentFilterCreator filterCreator
    ) {
        this.contentReader = contentReader;
        this.filterCreator = filterCreator;
    }

    public List<SubscribedContent> getList(Subscription subscription) {
        List<SubscribedContent> contents = new ArrayList<>();
        int page = 1;
        while (page <= MAX_PAGE) {
            List<SubscribedContent> currentPageContents = contentReader.getList(subscription, page);
            if (currentPageContents.isEmpty()) {
                break;
            }

            BatchCollectedSubscribedContentFilter contentFilter = filterCreator.create(currentPageContents);

            if (!contentFilter.hasNext()) {
                break;
            }

            if (page == MAX_PAGE && contentFilter.hasNext()) {
                log.warn("컨텐츠 수집이 최대 페이지[page={}]에 도달했으나, 수집이 완료되지 않았습니다.", page);
            }
            page += 1;
        }

        return contents;
    }

}
