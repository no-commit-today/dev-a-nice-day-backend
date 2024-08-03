package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.client.feed.FeedClient;
import com.nocommittoday.techswipe.client.feed.FeedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class SubscribedContentReaderFeedTest {

    @InjectMocks
    private SubscribedContentReaderFeed subscribedContentReaderFeed;

    @Mock
    private FeedClient feedClient;

    @Mock
    private DocumentConnector documentConnector;

    @Mock
    private LocalDateParser localDateParser;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

    @Test
    void feed_의_컨텐츠를_가져온다() {
        // given
        FeedResponse feedResponse = new FeedResponse(
                "provider-url",
                "provider-title",
                "provider-icon-url",
                List.of(
                        new FeedResponse.Entry(
                                "entry-url",
                                "entry-title",
                                LocalDate.of(2024, 6, 17),
                                "entry-content"
                        )
                )
        );
        given(feedClient.get("feed-url")).willReturn(ClientResponse.success(feedResponse));

        DocumentCrawler documentCrawler = mock(DocumentCrawler.class);
        given(documentCrawler.getImageUrl()).willReturn("entry-image-url");
        given(documentConnector.connect("entry-url")).willReturn(ClientResponse.success(documentCrawler));
        given(htmlTagCleaner.clean("entry-content")).willReturn("entry-content-cleaned");


        // when
        List<SubscribedContent> result = subscribedContentReaderFeed.getList(
                new FeedSubscription(
                        "feed-url",
                        new ContentCrawling(
                                new Crawling(CrawlingType.NONE, null, null),
                                new Crawling(CrawlingType.NONE, null, null),
                                new Crawling(CrawlingType.NONE, null, null)
                        )
                ),
                LocalDate.of(2024, 6, 17)
        );

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).url()).isEqualTo("entry-url");
        assertThat(result.get(0).title()).isEqualTo("entry-title");
        assertThat(result.get(0).imageUrl()).isEqualTo("entry-image-url");
        assertThat(result.get(0).content()).isEqualTo("entry-content-cleaned");
        assertThat(result.get(0).publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
    }

    @Test
    void date이상의_컨텐츠를_가져온다() {
        // given
        FeedResponse feedResponse = new FeedResponse(
                "provider-url",
                "provider-title",
                "provider-icon-url",
                List.of(
                        new FeedResponse.Entry(
                                "entry-url-1",
                                "entry-title-1",
                                LocalDate.of(2024, 6, 17),
                                "entry-content-1"
                        ),
                        new FeedResponse.Entry(
                                "entry-url-2",
                                "entry-title-2",
                                LocalDate.of(2024, 6, 16),
                                "entry-content-2"
                        )
                )
        );
        given(feedClient.get("feed-url")).willReturn(ClientResponse.success(feedResponse));

        DocumentCrawler documentCrawler = mock(DocumentCrawler.class);
        given(documentCrawler.getImageUrl()).willReturn("entry-image-url-1");
        given(documentConnector.connect("entry-url-1")).willReturn(ClientResponse.success(documentCrawler));
        given(documentConnector.connect("entry-url-2"))
                .willReturn(ClientResponse.success(mock(DocumentCrawler.class)));
        given(htmlTagCleaner.clean("entry-content-1")).willReturn("entry-content-1-cleaned");

        // when
        List<SubscribedContent> result = subscribedContentReaderFeed.getList(
                new FeedSubscription(
                        "feed-url",
                        new ContentCrawling(
                                new Crawling(CrawlingType.NONE, null, null),
                                new Crawling(CrawlingType.NONE, null, null),
                                new Crawling(CrawlingType.NONE, null, null)
                        )
                ),
                LocalDate.of(2024, 6, 17)
        );

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
    }

    @Test
    void 컨텐츠가_크롤링이_필요할경우_크롤링한_데이터를_반환한다() {
        // given
        FeedResponse feedResponse = new FeedResponse(
                "provider-url",
                "provider-title",
                "provider-icon-url",
                List.of(
                        new FeedResponse.Entry(
                                "entry-url",
                                "entry-title",
                                LocalDate.of(2024, 6, 17),
                                "entry-image-content"
                        )
                )
        );
        given(feedClient.get("feed-url")).willReturn(ClientResponse.success(feedResponse));

        DocumentCrawler documentCrawler = mock(DocumentCrawler.class);
        given(documentCrawler.getImageUrl()).willReturn("entry-image-url");
        given(documentConnector.connect("entry-url")).willReturn(ClientResponse.success(documentCrawler));

        Crawling titleCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(1)
        );
        Crawling dateCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(2)
        );
        Crawling contentCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(3)
        );

        given(documentCrawler.getText(dateCrawling)).willReturn("entry-date-crawl");
        given(localDateParser.parse("entry-date-crawl")).willReturn(LocalDate.of(2024, 6, 17));
        given(documentCrawler.getText(titleCrawling)).willReturn("entry-title-crawl");
        given(documentCrawler.get(contentCrawling)).willReturn("entry-content-crawl");
        given(htmlTagCleaner.clean("entry-content-crawl")).willReturn("entry-content-crawl-cleaned");

        // when
        List<SubscribedContent> result = subscribedContentReaderFeed.getList(
                new FeedSubscription(
                        "feed-url",
                        new ContentCrawling(
                                titleCrawling,
                                dateCrawling,
                                contentCrawling
                        )
                ),
                LocalDate.of(2024, 6, 17)
        );

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).url()).isEqualTo("entry-url");
        assertThat(result.get(0).title()).isEqualTo("entry-title-crawl");
        assertThat(result.get(0).publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
        assertThat(result.get(0).content()).isEqualTo("entry-content-crawl-cleaned");
        assertThat(result.get(0).imageUrl()).isEqualTo("entry-image-url");
    }
}