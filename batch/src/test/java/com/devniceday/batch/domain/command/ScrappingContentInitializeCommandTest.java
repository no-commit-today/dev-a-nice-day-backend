package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.ContentInitialization;
import com.devniceday.batch.domain.ContentNotFoundException;
import com.devniceday.batch.domain.HtmlScrapper;
import com.devniceday.batch.domain.LocalDateParser;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.test.SubscriptionFixture;
import com.devniceday.batch.jsoup.HtmlDocument;
import com.devniceday.batch.jsoup.HtmlDocumentClient;
import com.devniceday.batch.jsoup.HtmlTagCleaner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ScrappingContentInitializeCommandTest {

    @InjectMocks
    private ScrappingContentInitializeCommand scrappingContentInitializeCommand;

    @Mock
    private HtmlDocumentClient htmlDocumentClient;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

    @Mock
    private HtmlScrapper htmlScrapper;

    @Mock
    private LocalDateParser localDateParser;

    @Test
    void FEED_타입을_지원한다() {
        // given
        Subscription feed = SubscriptionFixture.createFeed();

        // when
        // then
        assertThat(scrappingContentInitializeCommand.supports(feed)).isTrue();
    }

    @Test
    void LIST_SCRAPPING_타입을_지원한다() {
        // given
        Subscription listScrapping = SubscriptionFixture.createWebList();

        // when
        // then
        assertThat(scrappingContentInitializeCommand.supports(listScrapping)).isTrue();
    }

    @Test
    void htmlDocument가_null인_경우_ContentNotFoundException을_던진다() {
        // given
        Subscription feed = SubscriptionFixture.createFeed();
        String url = "http://example.com";

        // when
        // then
        assertThatThrownBy(() -> scrappingContentInitializeCommand.initialize(feed, url))
                .isInstanceOf(ContentNotFoundException.class)
                .hasMessage("존재하지 않는 컨텐츠입니다. url=" + url);
    }

    @Test
    void 컨텐츠_초기화_데이터를_반환한다() {
        // given
        Subscription feed = SubscriptionFixture.createFeed();
        String url = "http://example.com";
        HtmlDocument htmlDocument = mock(HtmlDocument.class);
        given(htmlDocumentClient.get(url)).willReturn(htmlDocument);
        given(htmlScrapper.scrapText(htmlDocument, feed.feed().contentScrapping().title()))
                .willReturn("title");
        given(htmlScrapper.scrapImage(htmlDocument, feed.feed().contentScrapping().image()))
                .willReturn("image");
        given(htmlScrapper.scrapText(htmlDocument, feed.feed().contentScrapping().date()))
                .willReturn("2021-01-01");
        given(htmlScrapper.scrapHtml(htmlDocument, feed.feed().contentScrapping().content()))
                .willReturn("content");
        given(localDateParser.parse("2021-01-01"))
                .willReturn(LocalDate.of(2021, 1, 1));
        given(htmlTagCleaner.clean("content"))
                .willReturn("cleaned content");

        // when
        ContentInitialization contentInitialization = scrappingContentInitializeCommand.initialize(feed, url);

        // then
        assertThat(contentInitialization.title()).isEqualTo("title");
        assertThat(contentInitialization.imageUrl()).isEqualTo("image");
        assertThat(contentInitialization.publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(contentInitialization.content()).isEqualTo("cleaned content");
    }
}