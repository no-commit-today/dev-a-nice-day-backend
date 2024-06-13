package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionEntityTest {

    @Test
    void SubscriptionRegister로_부터_생성할_수_있다() {
        // given
        SubscriptionRegister subscriptionRegister = new SubscriptionRegister(
                new TechContentProvider.TechContentProviderId(1L),
                SubscriptionType.RSS,
                SubscriptionInitType.LIST_CRAWLING,
                "rssUrl",
                "atomUrl",
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
        assertThat(result.getType()).isEqualTo(SubscriptionType.RSS);
        assertThat(result.getInitType()).isEqualTo(SubscriptionInitType.LIST_CRAWLING);
        assertThat(result.getData().getRssUrl()).isEqualTo("rssUrl");
        assertThat(result.getData().getAtomUrl()).isEqualTo("atomUrl");
        assertThat(result.getData().getContentCrawling()).isEqualTo(new ContentCrawling(
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
        ));
        assertThat(result.getData().getListCrawlings()).containsExactly(
                new ListCrawling("url1", new Crawling(CrawlingType.INDEX, null, List.of(1, 2)), "pageUrlFormat")
        );
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        final SubscriptionEntity entity = new SubscriptionEntity(
                1L,
                TechContentProviderEntity.from(new TechContentProvider.TechContentProviderId(2L)),
                SubscriptionType.RSS,
                SubscriptionInitType.LIST_CRAWLING,
                new SubscriptionData(
                        "rssUrl",
                        "atomUrl",
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
                )
        );

        // when
        final Subscription result = entity.toDomain();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(new Subscription.SubscriptionId(1L));
        assertThat(result.getProviderId()).isEqualTo(new TechContentProvider.TechContentProviderId(2L));
        assertThat(result.getType()).isEqualTo(SubscriptionType.RSS);
        assertThat(result.getInitType()).isEqualTo(SubscriptionInitType.LIST_CRAWLING);
        assertThat(result.getRssUrl()).isEqualTo("rssUrl");
        assertThat(result.getAtomUrl()).isEqualTo("atomUrl");
        assertThat(result.getContentCrawling()).isEqualTo(new ContentCrawling(
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
        ));
        assertThat(result.getListCrawlings()).containsExactly(new ListCrawling("url1", new Crawling(CrawlingType.INDEX, null, List.of(1, 2)), "pageUrlFormat"));
    }
}