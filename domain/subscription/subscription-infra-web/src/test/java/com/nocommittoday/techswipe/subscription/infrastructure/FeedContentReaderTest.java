package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.client.core.ClientResponse;
import com.nocommittoday.client.feed.FeedClient;
import com.nocommittoday.client.feed.FeedResponse;
import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.FeedSubscription;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
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
class FeedContentReaderTest {

    @InjectMocks
    private FeedContentReader feedContentReader;

    @Mock
    private FeedClient feedClient;

    @Mock
    private ContentCrawlerCreator contentCrawlerCreator;

    @Mock
    private LocalDateParser localDateParser;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

    @Test
    void feed_의_컨텐츠를_가져온다() {
        // given
        final FeedResponse feedResponse = new FeedResponse(
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

        final ContentCrawler contentCrawler = mock(ContentCrawler.class);
        given(contentCrawler.getImageUrl()).willReturn("entry-image-url");
        given(contentCrawlerCreator.create("entry-url")).willReturn(contentCrawler);
        given(htmlTagCleaner.clean("entry-content")).willReturn("entry-content-cleaned");


        // when
        final List<SubscribedContentResult> result = feedContentReader.getList(
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
        assertThat(result.get(0).content().url()).isEqualTo("entry-url");
        assertThat(result.get(0).content().title()).isEqualTo("entry-title");
        assertThat(result.get(0).content().imageUrl()).isEqualTo("entry-image-url");
        assertThat(result.get(0).content().content()).isEqualTo("entry-content-cleaned");
        assertThat(result.get(0).content().publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
    }

    @Test
    void date이상의_컨텐츠를_가져온다() {
        // given
        final FeedResponse feedResponse = new FeedResponse(
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

        final ContentCrawler contentCrawler = mock(ContentCrawler.class);
        given(contentCrawler.getImageUrl()).willReturn("entry-image-url-1");
        given(contentCrawlerCreator.create("entry-url-1")).willReturn(contentCrawler);
        given(contentCrawlerCreator.create("entry-url-2"))
                .willReturn(mock(ContentCrawler.class));
        given(htmlTagCleaner.clean("entry-content-1")).willReturn("entry-content-1-cleaned");

        // when
        final List<SubscribedContentResult> result = feedContentReader.getList(
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
        assertThat(result.get(0).content().publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
    }

    @Test
    void 컨텐츠가_크롤링이_필요할경우_크롤링한_데이터를_반환한다() {
        // given
        final FeedResponse feedResponse = new FeedResponse(
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

        final ContentCrawler contentCrawler = mock(ContentCrawler.class);
        given(contentCrawler.getImageUrl()).willReturn("entry-image-url");
        given(contentCrawlerCreator.create("entry-url")).willReturn(contentCrawler);

        final Crawling titleCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(1)
        );
        final Crawling dateCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(2)
        );
        final Crawling contentCrawling = new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(3)
        );

        given(contentCrawler.getText(dateCrawling)).willReturn("entry-date-crawl");
        given(localDateParser.parse("entry-date-crawl")).willReturn(LocalDate.of(2024, 6, 17));
        given(contentCrawler.getText(titleCrawling)).willReturn("entry-title-crawl");
        given(contentCrawler.getCleaned(contentCrawling)).willReturn("entry-content-crawl");

        // when
        final List<SubscribedContentResult> result = feedContentReader.getList(
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
        assertThat(result.get(0).content().url()).isEqualTo("entry-url");
        assertThat(result.get(0).content().title()).isEqualTo("entry-title-crawl");
        assertThat(result.get(0).content().publishedDate()).isEqualTo(LocalDate.of(2024, 6, 17));
        assertThat(result.get(0).content().content()).isEqualTo("entry-content-crawl");
        assertThat(result.get(0).content().imageUrl()).isEqualTo("entry-image-url");
    }
}