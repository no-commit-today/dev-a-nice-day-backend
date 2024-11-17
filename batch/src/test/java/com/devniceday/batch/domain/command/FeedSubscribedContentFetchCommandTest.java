package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.FeedSubscription;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.feed.Feed;
import com.devniceday.batch.feed.FeedClient;
import com.devniceday.batch.jsoup.HtmlTagCleaner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FeedSubscribedContentFetchCommandTest {

    @InjectMocks
    private FeedSubscribedContentFetchCommand fetchCommand;

    @Mock
    private FeedClient feedClient;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

    @Test
    void SubscriptionType_이_FEED_이고_FeedSubscription_일_경우_지원한다() {
        // given
        Subscription feedSubscription = mock(Subscription.class);
        given(feedSubscription.type()).willReturn(SubscriptionType.FEED);

        Subscription listScrappingSubscription = mock(Subscription.class);
        given(listScrappingSubscription.type()).willReturn(SubscriptionType.LIST_SCRAPPING);

        // when
        // then
        assertThat(
                fetchCommand.supports(feedSubscription)
        ).isTrue();
        assertThat(
                fetchCommand.supports(listScrappingSubscription)
        ).isFalse();
    }

    @Test
    void 페이지가_1이_아닐경우_빈_리스트를_반환한다() {
        // given
        Subscription subscription = mock(Subscription.class);

        // when
        assertAll(
                () -> assertThat(fetchCommand.fetch(subscription, -1)).isEmpty(),
                () -> assertThat(fetchCommand.fetch(subscription, 0)).isEmpty(),
                () -> assertThat(fetchCommand.fetch(subscription, 2)).isEmpty()
        );
    }

    @Test
    void 피드를_읽어_구독_컨텐츠를_리스트로_변환한다() {
        // given
        Subscription subscription = mock(Subscription.class);
        given(subscription.id()).willReturn(1L);
        FeedSubscription feedSubscription = mock(FeedSubscription.class);
        given(feedSubscription.url()).willReturn("feed-url");
        given(subscription.feed()).willReturn(feedSubscription);
        given(feedClient.get("feed-url")).willReturn(
                new Feed(
                        "feed-link",
                        "feed-title",
                        "feed-icon-url",
                        List.of(
                                new Feed.Entry(
                                        "entry-link",
                                        "entry-title",
                                        LocalDate.of(2021, 1, 1),
                                        "entry-content"
                                )
                        )
                )
        );
        given(htmlTagCleaner.clean("entry-content")).willReturn("entry-content-cleaned");

        // when
        var result = fetchCommand.fetch(subscription, 1);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).subscriptionId()).isEqualTo(1);
        assertThat(result.get(0).url()).isEqualTo("entry-link");
        assertThat(result.get(0).title()).isEqualTo("entry-title");
        assertThat(result.get(0).publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(result.get(0).content()).isEqualTo("entry-content-cleaned");
        assertThat(result.get(0).imageUrl()).isNull();
    }
}