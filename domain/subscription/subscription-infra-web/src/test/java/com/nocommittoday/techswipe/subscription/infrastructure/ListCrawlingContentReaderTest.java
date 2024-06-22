package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawlingSubscription;
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
class ListCrawlingContentReaderTest {

    @InjectMocks
    private ListCrawlingContentReader listCrawlingContentReader;

    @Mock
    private DocumentConnector documentConnector;

    @Mock
    private DocumentElementExtractor documentElementExtractor;

    @Mock
    private UrlListCrawlingIteratorCreator urlListCrawlingIteratorCreator;

    @Mock
    private ContentCrawlerCreator contentCrawlerCreator;

    @Mock
    private LocalDateParser localDateParser;

    @Test
    void 컨텐츠_목록_리스트의_url을_크롤링한_후_url을_통해_컨텐츠를_크롤링한다() {
        // given
        final ListCrawling listCrawling = new ListCrawling(
                "list-url",
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(1)
                ),
                "page-url-format"
        );

        final UrlListCrawlingIterator iterator = mock(UrlListCrawlingIterator.class);
        given(iterator.hasNext()).willReturn(true, false);
        given(iterator.next()).willReturn("content-url-1");

        given(urlListCrawlingIteratorCreator.create(
                documentConnector,
                documentElementExtractor,
                listCrawling
        )).willReturn(iterator);

        final ContentCrawler contentCrawler = mock(ContentCrawler.class);
        given(contentCrawlerCreator.create(
                documentElementExtractor,
                documentConnector,
                "content-url-1"
        )).willReturn(contentCrawler);

        final ContentCrawling contentCrawling = new ContentCrawling(
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(2)
                ),
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(3)
                ),
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(4)
                )
        );

        given(contentCrawler.getText(contentCrawling.date())).willReturn("date-crawl-1");
        given(contentCrawler.getText(contentCrawling.title())).willReturn("title-crawl-1");
        given(contentCrawler.getImageUrl()).willReturn("image-url-1");
        given(contentCrawler.get(contentCrawling.content())).willReturn("content-crawl-1");
        given(localDateParser.parse("date-crawl-1")).willReturn(LocalDate.of(2024, 6, 17));

        // when
        final List<SubscribedContent> result = listCrawlingContentReader.getList(
                new ListCrawlingSubscription(
                        listCrawling,
                        contentCrawling
                ),
                LocalDate.of(2024, 6, 17)
        );

        // then
        assertThat(result).containsExactly(
                new SubscribedContent(
                        "content-url-1",
                        "title-crawl-1",
                        "image-url-1",
                        LocalDate.of(2024, 6, 17),
                        "content-crawl-1"
                )
        );
    }

    @Test
    void date_이상의_컨텐츠를_가져온다() {
        // given
        final ListCrawling listCrawling = new ListCrawling(
                "list-url",
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(1)
                ),
                "page-url-format"
        );

        final UrlListCrawlingIterator iterator = mock(UrlListCrawlingIterator.class);
        given(iterator.hasNext()).willReturn(true, true, false);
        given(iterator.next()).willReturn("content-url-1", "content-url-2");

        given(urlListCrawlingIteratorCreator.create(
                documentConnector,
                documentElementExtractor,
                listCrawling
        )).willReturn(iterator);

        final ContentCrawler contentCrawler1 = mock(ContentCrawler.class);
        given(contentCrawlerCreator.create(
                documentElementExtractor,
                documentConnector,
                "content-url-1"
        )).willReturn(contentCrawler1);

        final ContentCrawler contentCrawler2 = mock(ContentCrawler.class);
        given(contentCrawlerCreator.create(
                documentElementExtractor,
                documentConnector,
                "content-url-2"
        )).willReturn(contentCrawler2);

        final ContentCrawling contentCrawling = new ContentCrawling(
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(2)
                ),
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(3)
                ),
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(4)
                )
        );

        given(contentCrawler1.getText(contentCrawling.date())).willReturn("date-crawl-1");
        given(contentCrawler1.getText(contentCrawling.title())).willReturn("title-crawl-1");
        given(contentCrawler1.getImageUrl()).willReturn("image-url-1");
        given(contentCrawler1.get(contentCrawling.content())).willReturn("content-crawl-1");
        given(localDateParser.parse("date-crawl-1")).willReturn(LocalDate.of(2024, 6, 17));

        given(contentCrawler2.getText(contentCrawling.date())).willReturn("date-crawl-2");
        given(localDateParser.parse("date-crawl-2")).willReturn(LocalDate.of(2024, 6, 16));

        // when
        final List<SubscribedContent> result = listCrawlingContentReader.getList(
                new ListCrawlingSubscription(
                        listCrawling,
                        contentCrawling
                ),
                LocalDate.of(2024, 6, 17)
        );

        // then
        assertThat(result).containsExactly(
                new SubscribedContent(
                        "content-url-1",
                        "title-crawl-1",
                        "image-url-1",
                        LocalDate.of(2024, 6, 17),
                        "content-crawl-1"
                )
        );
    }
}