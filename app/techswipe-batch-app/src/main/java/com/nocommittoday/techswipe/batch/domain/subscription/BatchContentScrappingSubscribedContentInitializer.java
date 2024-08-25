package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.batch.domain.subscription.exception.NotSupportedSubscriptionException;
import com.nocommittoday.techswipe.domain.subscription.ContentScrapping;
import com.nocommittoday.techswipe.domain.subscription.FeedSubscription;
import com.nocommittoday.techswipe.domain.subscription.ListScrappingSubscription;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionType;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlClient;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlDocument;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlDocumentCreator;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlTagCleaner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BatchContentScrappingSubscribedContentInitializer implements BatchSubscribedContentInitializer {

    private final HtmlClient htmlClient;
    private final HtmlDocumentCreator htmlDocumentCreator;
    private final BatchLocalDateParser localDateParser;
    private final HtmlTagCleaner htmlTagCleaner;

    public BatchContentScrappingSubscribedContentInitializer(
            HtmlClient htmlClient,
            HtmlDocumentCreator htmlDocumentCreator,
            BatchLocalDateParser localDateParser,
            HtmlTagCleaner htmlTagCleaner
    ) {
        this.htmlClient = htmlClient;
        this.htmlDocumentCreator = htmlDocumentCreator;
        this.localDateParser = localDateParser;
        this.htmlTagCleaner = htmlTagCleaner;
    }

    @Override
    public SubscribedContent initialize(Subscription subscription, SubscribedContent content) {
        ContentScrapping contentScrapping = getContentScrapping(subscription);
        String html = htmlClient.get(content.getUrl()).getData();
        HtmlDocument htmlDocument = htmlDocumentCreator.create(html, content.getUrl());
        return content.initialize(
                DocumentScrappingHelper.scrap(htmlDocument, contentScrapping.getTitle()),
                DocumentScrappingHelper.scrap(htmlDocument, contentScrapping.getImage()),
                Optional.ofNullable(DocumentScrappingHelper.scrap(htmlDocument, contentScrapping.getDate()))
                        .map(localDateParser::parse)
                        .orElse(null),
                Optional.ofNullable(DocumentScrappingHelper.html(htmlDocument, contentScrapping.getContent()))
                        .map(htmlTagCleaner::clean)
                        .orElse(null)
        );
    }

    private ContentScrapping getContentScrapping(Subscription subscription) {
        if (subscription.getType() == SubscriptionType.FEED) {
            return ((FeedSubscription) subscription).getContentScrapping();
        } else if (subscription.getType() == SubscriptionType.LIST_SCRAPPING) {
            return ((ListScrappingSubscription) subscription).getContentScrapping();
        }
        throw new NotSupportedSubscriptionException(subscription.getId());
    }

    @Override
    public boolean supports(Subscription subscription) {
        return (subscription.getType() == SubscriptionType.FEED
                && subscription instanceof FeedSubscription)
                || (subscription.getType() == SubscriptionType.LIST_SCRAPPING
                && subscription instanceof ListScrappingSubscription);
    }
}
