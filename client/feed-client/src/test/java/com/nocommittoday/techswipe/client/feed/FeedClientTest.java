package com.nocommittoday.techswipe.client.feed;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.feed.synd.SyndImageImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeedClientTest {

    @InjectMocks
    private FeedClient feedClient;

    @Mock
    private XmlClient xmlClient;

    @Mock
    private SyndFeedBuilder syndFeedBuilder;

    @Test
    void SyndFeed를_FeedResponse로_변환한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("icon-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isEqualTo("entry-content");
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_Icon이_없으면_Image를_선택한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        syndFeed.setIcon(null);

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("image-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isEqualTo("entry-content");
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_Icon과_Image_둘다_없으면_null을_반환한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        syndFeed.setIcon(null);
        syndFeed.setImage(null);

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isNull();
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isEqualTo("entry-content");
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_publishedDate가_없으면_updatedDate를_선택한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        final SyndEntry syndEntry = createSyndEntry();
        syndEntry.setPublishedDate(null);
        syndFeed.setEntries(List.of(syndEntry));

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("icon-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 2));
        assertThat(entry.content()).isEqualTo("entry-content");
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_content가_없으면_description을_선택한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        final SyndEntry syndEntry = createSyndEntry();
        syndEntry.setContents(List.of());
        syndFeed.setEntries(List.of(syndEntry));

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("icon-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isEqualTo("entry-description");
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_content와_description이_없으면_null을_반환한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        final SyndEntry syndEntry = createSyndEntry();
        syndEntry.setContents(List.of());
        syndEntry.setDescription(null);
        syndFeed.setEntries(List.of(syndEntry));

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("icon-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isNull();
    }

    @Test
    void SyndFeed를_FeedResponse로_변환할때_content가_여러개일_경우_가장_긴_것을_선택한다() {
        // given
        final SyndFeed syndFeed = createSyndFeed();
        final SyndEntry syndEntry = createSyndEntry();
        final SyndContentImpl syndContent1 = new SyndContentImpl();
        syndContent1.setValue("short-content");
        final SyndContentImpl syndContent2 = new SyndContentImpl();
        syndContent2.setValue("very-very-very-very-long-content");
        syndEntry.setContents(List.of(
                syndContent1,
                syndContent2
        ));
        syndFeed.setEntries(List.of(syndEntry));

        given(xmlClient.get("url")).willReturn("xml");
        given(syndFeedBuilder.build("xml")).willReturn(syndFeed);

        // when
        final FeedResponse feedResponse = feedClient.get("url").getData();

        // then
        assertThat(feedResponse.link()).isEqualTo("blog-link");
        assertThat(feedResponse.title()).isEqualTo("blog-title");
        assertThat(feedResponse.iconUrl()).isEqualTo("icon-url");
        assertThat(feedResponse.entries()).hasSize(1);

        final FeedResponse.Entry entry = feedResponse.entries().get(0);
        assertThat(entry.link()).isEqualTo("entry-link");
        assertThat(entry.title()).isEqualTo("entry-title");
        assertThat(entry.date()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entry.content()).isEqualTo("very-very-very-very-long-content");
    }

    private SyndFeed createSyndFeed() {
        final SyndFeedImpl syndFeed = new SyndFeedImpl();
        syndFeed.setLink("blog-link");
        syndFeed.setTitle("blog-title");
        final SyndImageImpl icon = new SyndImageImpl();
        icon.setUrl("icon-url");
        icon.setLink("icon-link");
        syndFeed.setIcon(icon);
        final SyndImageImpl image = new SyndImageImpl();
        image.setUrl("image-url");
        image.setLink("image-link");
        syndFeed.setImage(image);

        final SyndEntry syndEntry = createSyndEntry();
        syndFeed.setEntries(List.of(syndEntry));

        return syndFeed;
    }

    private static SyndEntry createSyndEntry() {
        final SyndEntryImpl syndEntry = new SyndEntryImpl();
        syndEntry.setLink("entry-link");
        syndEntry.setTitle("entry-title");
        syndEntry.setPublishedDate(Date.from(LocalDate.of(2021, 1, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        syndEntry.setUpdatedDate(Date.from(LocalDate.of(2021, 1, 2)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        final SyndContentImpl syndContent = new SyndContentImpl();
        syndContent.setValue("entry-content");
        syndEntry.setContents(List.of(
                syndContent
        ));
        final SyndContentImpl syndDescription = new SyndContentImpl();
        syndDescription.setValue("entry-description");
        syndEntry.setDescription(syndDescription);
        return syndEntry;
    }
}