package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawlingSubscription;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
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
class SubscribedContentReaderListCrawlingTest {

    @InjectMocks
    private SubscribedContentReaderListCrawling subscribedContentReaderListCrawling;

    @Mock
    private UrlListCrawlingIteratorCreator urlListCrawlingIteratorCreator;

    @Mock
    private DocumentConnector documentConnector;

    @Mock
    private LocalDateParser localDateParser;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

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

        given(urlListCrawlingIteratorCreator.create(listCrawling)).willReturn(iterator);

        final DocumentCrawler documentCrawler = mock(DocumentCrawler.class);
        given(documentConnector.connect("content-url-1")).willReturn(ClientResponse.success(documentCrawler));

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

        given(documentCrawler.getText(contentCrawling.date())).willReturn("date-crawl-1");
        given(documentCrawler.getText(contentCrawling.title())).willReturn("title-crawl-1");
        given(documentCrawler.getImageUrl()).willReturn("image-url-1");
        given(documentCrawler.get(contentCrawling.content())).willReturn("content-crawl-1");
        given(htmlTagCleaner.clean("content-crawl-1")).willReturn("content-crawl-1-cleaned");
        given(localDateParser.parse("date-crawl-1")).willReturn(LocalDate.of(2024, 6, 17));

        // when
        final List<SubscribedContent> result = subscribedContentReaderListCrawling.getList(
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
                        "content-crawl-1-cleaned"
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

        given(urlListCrawlingIteratorCreator.create(listCrawling)).willReturn(iterator);

        final DocumentCrawler documentCrawler1 = mock(DocumentCrawler.class);
        given(documentConnector.connect("content-url-1")).willReturn(ClientResponse.success(documentCrawler1));

        final DocumentCrawler documentCrawler2 = mock(DocumentCrawler.class);
        given(documentConnector.connect("content-url-2")).willReturn(ClientResponse.success(documentCrawler2));

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

        given(documentCrawler1.getText(contentCrawling.date())).willReturn("date-crawl-1");
        given(documentCrawler1.getText(contentCrawling.title())).willReturn("title-crawl-1");
        given(documentCrawler1.getImageUrl()).willReturn("image-url-1");
        given(documentCrawler1.get(contentCrawling.content())).willReturn("content-crawl-1");
        given(htmlTagCleaner.clean("content-crawl-1")).willReturn("content-crawl-1-cleaned");
        given(localDateParser.parse("date-crawl-1")).willReturn(LocalDate.of(2024, 6, 17));

        given(documentCrawler2.getText(contentCrawling.date())).willReturn("date-crawl-2");
        given(localDateParser.parse("date-crawl-2")).willReturn(LocalDate.of(2024, 6, 16));

        // when
        final List<SubscribedContent> result = subscribedContentReaderListCrawling.getList(
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
                        "content-crawl-1-cleaned"
                )
        );
    }
}