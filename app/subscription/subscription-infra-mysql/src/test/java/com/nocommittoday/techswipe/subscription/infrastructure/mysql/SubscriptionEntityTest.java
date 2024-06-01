package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionEntityTest {

    @Test
    void toDomain() {
        // given
        final SubscriptionEntity entity = new SubscriptionEntity(
                1L,
                new TechContentProviderIdEmbeddable(2L),
                SubscriptionType.RSS,
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