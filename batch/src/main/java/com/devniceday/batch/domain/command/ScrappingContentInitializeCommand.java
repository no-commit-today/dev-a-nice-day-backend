package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.ContentInitialization;
import com.devniceday.batch.domain.ContentInitializeCommand;
import com.devniceday.batch.domain.ContentNotFoundException;
import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.HtmlScrapper;
import com.devniceday.batch.domain.LocalDateParser;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.jsoup.HtmlDocument;
import com.devniceday.batch.jsoup.HtmlDocumentClient;
import com.devniceday.batch.jsoup.HtmlTagCleaner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ScrappingContentInitializeCommand implements ContentInitializeCommand {

    private final HtmlDocumentClient htmlDocumentClient;
    private final HtmlTagCleaner htmlTagCleaner;
    private final HtmlScrapper htmlScrapper;
    private final LocalDateParser localDateParser;

    public ScrappingContentInitializeCommand(
            HtmlDocumentClient htmlDocumentClient,
            HtmlTagCleaner htmlTagCleaner,
            HtmlScrapper htmlScrapper,
            LocalDateParser localDateParser
    ) {
        this.htmlDocumentClient = htmlDocumentClient;
        this.htmlTagCleaner = htmlTagCleaner;
        this.htmlScrapper = htmlScrapper;
        this.localDateParser = localDateParser;
    }

    @Override
    public boolean supports(Subscription subscription) {
        return subscription.type() == SubscriptionType.FEED
                || subscription.type() == SubscriptionType.LIST_SCRAPPING;
    }

    @Override
    public ContentInitialization initialize(Subscription subscription, String url) {
        HtmlDocument htmlDocument = htmlDocumentClient.get(url);
        if (htmlDocument == null) {
            throw new ContentNotFoundException("존재하지 않는 컨텐츠입니다. url=" + url);
        }

        ContentScrapping contentScrapping = getContentScrapping(subscription);
        return new ContentInitialization(
                htmlScrapper.scrapText(htmlDocument, contentScrapping.title()),
                htmlScrapper.scrapImage(htmlDocument, contentScrapping.image()),
                Optional.ofNullable(htmlScrapper.scrapText(htmlDocument, contentScrapping.date()))
                        .map(localDateParser::parse)
                        .orElse(null),
                Optional.ofNullable(htmlScrapper.scrapHtml(htmlDocument, contentScrapping.content()))
                        .map(htmlTagCleaner::clean)
                        .orElse(null)
        );
    }

    private ContentScrapping getContentScrapping(Subscription subscription) {
        if (subscription.type() == SubscriptionType.FEED) {
            return Objects.requireNonNull(subscription.feed()).contentScrapping();
        } else if (subscription.type() == SubscriptionType.LIST_SCRAPPING) {
            return Objects.requireNonNull(subscription.webList()).contentScrapping();
        }

        throw new IllegalStateException("스크래핑 초기화를 지원하지 않는 구독 입니다." +
                " subscriptionId=" + subscription.id() + ", type=" + subscription.type());
    }
}
