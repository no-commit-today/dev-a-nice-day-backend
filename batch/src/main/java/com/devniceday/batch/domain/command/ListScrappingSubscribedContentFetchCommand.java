package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.HtmlScrapper;
import com.devniceday.batch.domain.ListScrapping;
import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.batch.domain.SubscribedContentFetchCommand;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.domain.WebListSubscription;
import com.devniceday.batch.jsoup.HtmlDocument;
import com.devniceday.batch.jsoup.HtmlDocumentClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ListScrappingSubscribedContentFetchCommand implements SubscribedContentFetchCommand {

    private final HtmlDocumentClient htmlDocumentClient;
    private final HtmlScrapper htmlScrapper;

    public ListScrappingSubscribedContentFetchCommand(
            HtmlDocumentClient htmlDocumentClient, HtmlScrapper htmlScrapper) {
        this.htmlDocumentClient = htmlDocumentClient;
        this.htmlScrapper = htmlScrapper;
    }

    @Override
    public List<SubscribedContent> fetch(Subscription subscription, int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("페이지는 1 이상이어야 합니다. page=" + page);
        }
        WebListSubscription webListSubscription = Objects.requireNonNull(subscription.webList());
        ListScrapping listScrapping = webListSubscription.listScrapping();

        if (page == 1) {
            String url = listScrapping.url();
            HtmlDocument htmlDocument = htmlDocumentClient.get(url);
            if (htmlDocument == null) {
                throw new IllegalStateException("존재하지 않는 페이지 입니다. subscriptionId=" +
                        subscription.id() + ", url=" + url);
            }
            return getNewSubscribedContents(subscription, htmlDocument);
        } else if (listScrapping.isPaginated()) {
            String url = listScrapping.pageUrl(page);
            HtmlDocument htmlDocument = htmlDocumentClient.get(url);
            if (htmlDocument == null) {
                return List.of();
            }

            return getNewSubscribedContents(subscription, htmlDocument);
        }

        return List.of();
    }

    private List<SubscribedContent> getNewSubscribedContents(
            Subscription subscription, HtmlDocument htmlDocument) {
        ListScrapping listScrapping = Objects.requireNonNull(subscription.webList()).listScrapping();
        List<String> contentUrls = htmlScrapper.scrapUrls(
                htmlDocument, listScrapping.scrapping());

        return contentUrls.stream()
                .filter(listScrapping::isContentUrl)
                .map(contentUrl -> new SubscribedContent(
                        subscription.id(),
                        contentUrl,
                        null,
                        null,
                        null,
                        null
                ))
                .toList();
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.LIST_SCRAPPING == subscription.type();
    }
}
