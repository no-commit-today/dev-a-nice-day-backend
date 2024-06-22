package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionEntityTest {

    @Test
    void SubscriptionRegister로_부터_생성할_수_있다() {
        // given
        SubscriptionRegister subscriptionRegister = new SubscriptionRegister(
                new TechContentProvider.Id(1L),
                SubscriptionType.FEED,
                SubscriptionType.LIST_CRAWLING,
                "feedUrl",
                new ContentCrawling(
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
                ),
                List.of(
                        new ListCrawling(
                                "url1",
                                new Crawling(CrawlingType.INDEX, null, List.of(1, 2)),
                                "pageUrlFormat"
                        )
                )
        );

        // when
        SubscriptionEntity result = SubscriptionEntity.from(subscriptionRegister);

        // then
        assertThat(result.getId()).isNull();
        assertThat(result.getProvider().getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo(SubscriptionType.FEED);
        assertThat(result.getInitType()).isEqualTo(SubscriptionType.LIST_CRAWLING);
        assertThat(result.getData().getFeed().getUrl()).isEqualTo("feedUrl");
        assertThat(result.getData().getContentCrawling()).isEqualTo(new ContentCrawling(
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
        ));
        assertThat(result.getData().getListCrawling().getContent()).containsExactly(
                new ListCrawling("url1", new Crawling(CrawlingType.INDEX, null, List.of(1, 2)), "pageUrlFormat")
        );
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        final SubscriptionEntity entity = new SubscriptionEntity(
                1L,
                TechContentProviderEntity.from(new TechContentProvider.Id(2L)),
                SubscriptionType.FEED,
                SubscriptionType.LIST_CRAWLING,
                new SubscriptionData(
                        new FeedData("feedUrl"),
                        new ContentCrawling(
                                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
                        ),
                        new ListCrawlingData(List.of(
                                new ListCrawling(
                                        "url1",
                                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2)),
                                        "pageUrlFormat"
                                ))
                        )
                )
        );

        // when
        final Subscription result = entity.toDomain();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(new Subscription.Id(1L));
        assertThat(result.getProviderId()).isEqualTo(new TechContentProvider.Id(2L));
        assertThat(result.getType()).isEqualTo(SubscriptionType.FEED);
        assertThat(result.getInitType()).isEqualTo(SubscriptionType.LIST_CRAWLING);
        assertThat(result.getFeedUrl()).isEqualTo("feedUrl");
        assertThat(result.getContentCrawling()).isEqualTo(new ContentCrawling(
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
        ));
        assertThat(result.getListCrawlings()).containsExactly(new ListCrawling("url1", new Crawling(CrawlingType.INDEX, null, List.of(1, 2)), "pageUrlFormat"));
    }
}