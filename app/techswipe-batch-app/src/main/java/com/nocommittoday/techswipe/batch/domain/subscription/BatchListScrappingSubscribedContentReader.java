package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.batch.domain.subscription.exception.NotSupportedSubscriptionException;
import com.nocommittoday.techswipe.domain.subscription.IndexScrapping;
import com.nocommittoday.techswipe.domain.subscription.ListScrapping;
import com.nocommittoday.techswipe.domain.subscription.ListScrappingSubscription;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.Scrapping;
import com.nocommittoday.techswipe.domain.subscription.ScrappingType;
import com.nocommittoday.techswipe.domain.subscription.SelectorScrapping;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionType;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlClient;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlDocument;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlDocumentCreator;
import com.nocommittoday.techswipe.infrastructure.web.ClientResponse;
import com.nocommittoday.techswipe.infrastructure.web.ClientResponseType;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public class BatchListScrappingSubscribedContentReader implements BatchSubscribedContentReader {

    private final HtmlClient htmlClient;
    private final HtmlDocumentCreator htmlDocumentCreator;

    public BatchListScrappingSubscribedContentReader(HtmlClient htmlClient, HtmlDocumentCreator htmlDocumentCreator) {
        this.htmlClient = htmlClient;
        this.htmlDocumentCreator = htmlDocumentCreator;
    }

    @Override
    public List<SubscribedContent> getList(Subscription subscription, int page) {
        ListScrappingSubscription listScrappingSubscription = (ListScrappingSubscription) subscription;
        ListScrapping listScrapping = listScrappingSubscription.getListScrapping();

        if (page == 1) {
            String url = listScrapping.getUrl();

            String html = htmlClient.get(url).getData();
            return getNewSubscribedContents(listScrappingSubscription, html, url);
        } else if (listScrapping.isPaginated()) {
            String url = listScrapping.getPageUrl(page);
            ClientResponse<String> htmlResponse = htmlClient.get(url);
            if (ClientResponseType.NOT_FOUND == htmlResponse.getType()) {
                return List.of();
            }

            String html = htmlResponse.getData();
            return getNewSubscribedContents(listScrappingSubscription, html, url);
        }

        return List.of();
    }

    private List<SubscribedContent> getNewSubscribedContents(
            ListScrappingSubscription subscription, String html, String url) {
        HtmlDocument htmlDocument = htmlDocumentCreator.create(html, url);
        List<String> contentUrls = new LinkedHashSet<>(scrapUrls(htmlDocument, subscription))
                .stream().toList();

        return contentUrls.stream()
                .filter(contentUrl -> subscription.getListScrapping().isContentUrl(contentUrl))
                .map(contentUrl -> new SubscribedContent(
                        subscription.getId(),
                        contentUrl,
                        !subscription.isInitRequired(),
                        null,
                        null,
                        null,
                        null
                ))
                .toList();
    }

    private List<String> scrapUrls(HtmlDocument document, ListScrappingSubscription subscription) {
        Scrapping scrapping = subscription.getListScrapping().getScrapping();
        if (ScrappingType.SELECTOR == scrapping.getType() && scrapping instanceof SelectorScrapping selectorScrapping) {
            return document.urlListBySelector(selectorScrapping.getSelector());
        } else if (ScrappingType.INDEX == scrapping.getType() && scrapping instanceof IndexScrapping indexScrapping) {
            return document.urlListByIndexes(indexScrapping.getIndexes());
        }

        throw new NotSupportedSubscriptionException(subscription.getId());
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.LIST_SCRAPPING == subscription.getType()
                && subscription instanceof ListScrappingSubscription;
    }
}
