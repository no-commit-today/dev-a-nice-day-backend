package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.HtmlScrapper;
import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.domain.test.ListScrappingFixture;
import com.devniceday.batch.domain.test.SubscriptionFixture;
import com.devniceday.batch.domain.test.WebListSubscriptionFixture;
import com.devniceday.batch.jsoup.HtmlDocument;
import com.devniceday.batch.jsoup.HtmlDocumentClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ListScrappingSubscribedContentFetchCommandTest {

    @InjectMocks
    private ListScrappingSubscribedContentFetchCommand fetchCommand;

    @Mock
    private HtmlDocumentClient htmlDocumentClient;

    @Mock
    private HtmlScrapper htmlScrapper;

    @Test
    void SubscriptionType_이_LIST_SCRAPPING_일_경우_지원한다() {
        // given
        Subscription listScrappingSubscription = SubscriptionFixture.createWebList();

        Subscription feedSubscription = SubscriptionFixture.createFeed();

        // when
        // then
        assertThat(fetchCommand.supports(listScrappingSubscription)).isTrue();
        assertThat(fetchCommand.supports(feedSubscription)).isFalse();
    }

    @Test
    void 페이지가_0이하일_경우_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> fetchCommand.fetch(SubscriptionFixture.createWebList(), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 페이지가_1인데_페이지가_존재하지_않을_경우_예외가_발생한다() {
        // given
        Subscription subscription = SubscriptionFixture.createWebList();

        // when
        // then
        assertThatThrownBy(() -> fetchCommand.fetch(subscription, 1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 페이지가_1이상인데_페이지가_존재하지_않을_경우_빈_리스트를_반환한다() {
        // given
        Subscription subscription = new SubscriptionFixture()
                .type(SubscriptionType.LIST_SCRAPPING)
                .webList(new WebListSubscriptionFixture()
                        .listScrapping(
                                new ListScrappingFixture()
                                        .url("http://example.com")
                                        .pageUrlFormat("http://example.com?page={page}")
                                        .build()
                        ).build()
                ).build();

        // when
        // then
        assertThat(fetchCommand.fetch(subscription, 2)).isEmpty();
    }

    @Test
    void 페이지가_1이상인데_페이지를_지원하지_않을_경우_빈_리스트를_반환한다() {
        // given
        Subscription subscription = SubscriptionFixture.createWebList();

        // when
        // then
        assertThat(fetchCommand.fetch(subscription, 2)).isEmpty();
    }

    @Test
    void 페이지_1일때_기본_url_로_컨텐츠를_읽어온다() {
        // given
        Subscription subscription = SubscriptionFixture.createWebList();
        HtmlDocument htmlDocument = mock(HtmlDocument.class);
        given(htmlDocumentClient.get(subscription.webList().listScrapping().url())).willReturn(htmlDocument);
        given(htmlScrapper.scrapUrls(htmlDocument, subscription.webList().listScrapping().scrapping()))
                .willReturn(List.of("http://example.com/content1", "http://example.com/content2"));

        // when
        List<SubscribedContent> result = fetchCommand.fetch(subscription, 1);

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly(
                        new SubscribedContent(subscription.id(), "http://example.com/content1", null, null, null, null),
                        new SubscribedContent(subscription.id(), "http://example.com/content2", null, null, null, null)
                );
    }

    @Test
    void 페이지_2이상일때_pageurl_로_컨텐츠를_읽어온다() {
        // given
        Subscription subscription = new SubscriptionFixture()
                .type(SubscriptionType.LIST_SCRAPPING)
                .webList(new WebListSubscriptionFixture()
                        .listScrapping(
                                new ListScrappingFixture()
                                        .url("http://example.com")
                                        .pageUrlFormat("http://example.com?page={page}")
                                        .build()
                        ).build()
                ).build();
        HtmlDocument htmlDocument = mock(HtmlDocument.class);
        given(htmlDocumentClient.get("http://example.com?page=2")).willReturn(htmlDocument);
        given(htmlScrapper.scrapUrls(htmlDocument, subscription.webList().listScrapping().scrapping()))
                .willReturn(List.of("http://example.com/content1", "http://example.com/content2"));
        int page = 2;

        // when
        List<SubscribedContent> result = fetchCommand.fetch(subscription, page);

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly(
                        new SubscribedContent(subscription.id(), "http://example.com/content1", null, null, null, null),
                        new SubscribedContent(subscription.id(), "http://example.com/content2", null, null, null, null)
                );
    }

    @Test
    void 컨텐츠_url_포맷에_맞지_않으면_무시한다() {
        // given
        Subscription subscription = new SubscriptionFixture()
                .type(SubscriptionType.LIST_SCRAPPING)
                .webList(new WebListSubscriptionFixture()
                        .listScrapping(
                                new ListScrappingFixture()
                                        .contentUrlFormat("http://example.com/{contentId}")
                                        .build()
                        ).build()
                ).build();
        HtmlDocument htmlDocument = mock(HtmlDocument.class);
        given(htmlDocumentClient.get(subscription.webList().listScrapping().url())).willReturn(htmlDocument);
        given(htmlScrapper.scrapUrls(htmlDocument, subscription.webList().listScrapping().scrapping()))
                .willReturn(List.of(
                        "http://example.com/content1",
                        "http://example.com/aasdf/asdf",
                        "http://strange.com/content2"
                ));

        // when
        List<SubscribedContent> result = fetchCommand.fetch(subscription, 1);

        // then
        assertThat(result)
                .hasSize(1)
                .containsExactly(
                        new SubscribedContent(subscription.id(), "http://example.com/content1", null, null, null, null)
                );
    }
}