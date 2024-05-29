package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
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
                        new ContentCrawlingIndexes(
                                List.of(1, 2, 3),
                                List.of(4, 5, 6),
                                List.of(7, 8, 9)
                        ),
                        List.of(
                                new ListCrawling("url1", List.of(1, 2, 3), "pageUrlFormat")
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
        assertThat(result.getContentCrawlingIndexes()).isEqualTo(new ContentCrawlingIndexes(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        ));
        assertThat(result.getListCrawlings()).containsExactly(new ListCrawling("url1", List.of(1, 2, 3), "pageUrlFormat"));
    }
}