package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.domain.subscription.FeedSubscription;
import com.nocommittoday.techswipe.domain.test.FeedSubscriptionBuilder;
import com.nocommittoday.techswipe.domain.test.ListScrappingSubscriptionBuilder;
import com.nocommittoday.techswipe.infrastructure.feed.Feed;
import com.nocommittoday.techswipe.infrastructure.feed.FeedReader;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlTagCleaner;
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

@ExtendWith(MockitoExtension.class)
class BatchFeedSubscribedContentReaderTest {

    @InjectMocks
    private BatchFeedSubscribedContentReader batchFeedSubscribedContentReader;

    @Mock
    private FeedReader feedReader;

    @Mock
    private HtmlTagCleaner htmlTagCleaner;

    @Test
    void SubscriptionType_이_FEED_이고_FeedSubscription_일_경우_지원한다() {
        // given
        // when
        // then
        assertThat(
                batchFeedSubscribedContentReader.supports(FeedSubscriptionBuilder.create())
        ).isTrue();
        assertThat(
                batchFeedSubscribedContentReader.supports(ListScrappingSubscriptionBuilder.create())
        ).isFalse();
    }

    @Test
    void 페이지가_1이_아닐경우_빈_리스트를_반환한다() {
        // given
        FeedSubscription feedSubscription = FeedSubscriptionBuilder.create();

        // when
        assertAll(
                () -> assertThat(batchFeedSubscribedContentReader.getList(feedSubscription, -1)).isEmpty(),
                () -> assertThat(batchFeedSubscribedContentReader.getList(feedSubscription, 0)).isEmpty(),
                () -> assertThat(batchFeedSubscribedContentReader.getList(feedSubscription, 2)).isEmpty()
        );
    }

    @Test
    void 피드를_읽어_구독_컨텐츠를_리스트로_변환한다() {
        // given
        FeedSubscription feedSubscription = FeedSubscriptionBuilder.create();
        given(feedReader.read(feedSubscription.getUrl())).willReturn(
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
        var result = batchFeedSubscribedContentReader.getList(feedSubscription, 1);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSubscriptionId()).isEqualTo(feedSubscription.getId());
        assertThat(result.get(0).getUrl()).isEqualTo("entry-link");
        assertThat(result.get(0).isInitialized()).isFalse();
        assertThat(result.get(0).getTitle()).isEqualTo("entry-title");
        assertThat(result.get(0).getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(result.get(0).getContent()).isEqualTo("entry-content-cleaned");
        assertThat(result.get(0).getImageUrl()).isNull();
    }
}