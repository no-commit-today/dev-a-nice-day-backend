package com.devniceday.batch.job;

import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.FeedSubscription;
import com.devniceday.batch.domain.ImageScrapping;
import com.devniceday.batch.domain.ListScrapping;
import com.devniceday.batch.domain.Scrapping;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.WebListSubscription;
import com.devniceday.storage.db.core.BatchContentScrappingData;
import com.devniceday.storage.db.core.BatchFeedSubscriptionData;
import com.devniceday.storage.db.core.BatchImageScrappingData;
import com.devniceday.storage.db.core.BatchListScrappingData;
import com.devniceday.storage.db.core.BatchScrappingData;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import com.devniceday.storage.db.core.BatchWebListSubscriptionData;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public Subscription map(BatchSubscriptionEntity entity) {
        return new Subscription(
                entity.getId(),
                entity.getProviderId(),
                entity.getType(),
                mapFeedSubscription(entity),
                mapWebListSubscription(entity)
        );
    }

    @Nullable
    private FeedSubscription mapFeedSubscription(BatchSubscriptionEntity entity) {
        if (entity.getFeed() == null) {
            return null;
        }

        BatchFeedSubscriptionData feed = entity.getFeed();
        BatchContentScrappingData contentScrapping = feed.getContentScrapping();

        return new FeedSubscription(
                feed.getUrl(),
                mapContentScrapping(contentScrapping)
        );
    }

    @Nullable
    private WebListSubscription mapWebListSubscription(BatchSubscriptionEntity entity) {
        if (entity.getWebList() == null) {
            return null;
        }

        BatchWebListSubscriptionData webList = entity.getWebList();


        return new WebListSubscription(
                mapListScrapping(webList.getListScrapping()),
                mapContentScrapping(webList.getContentScrapping())
        );
    }

    private ListScrapping mapListScrapping(BatchListScrappingData scrapping) {
        return new ListScrapping(
                scrapping.getUrl(),
                mapScrapping(scrapping.getScrapping()),
                scrapping.getPageUrlFormat(),
                scrapping.getContentUrlFormat()
        );
    }

    private Scrapping mapScrapping(BatchScrappingData scrapping) {
        return new Scrapping(scrapping.getType(), scrapping.getIndexes(), scrapping.getSelector());
    }

    private ContentScrapping mapContentScrapping(BatchContentScrappingData contentScrapping) {
        return new ContentScrapping(
                mapScrapping(contentScrapping.getTitle()),
                mapScrapping(contentScrapping.getDate()),
                mapScrapping(contentScrapping.getContent()),
                mapImageScrapping(contentScrapping.getImage())
        );
    }

    private ImageScrapping mapImageScrapping(BatchImageScrappingData scrapping) {
        return new ImageScrapping(scrapping.getType());
    }
}
