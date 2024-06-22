package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegisterFailureException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionRegisterTest {

    @Test
    void FEED_타입일경우_feedUrl이_필요하다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new SubscriptionRegister(
                new TechContentProvider.Id(1),
                SubscriptionType.FEED,
                SubscriptionInitType.NONE,
                null,
                new ContentCrawling(
                        new Crawling(CrawlingType.NONE, null, null),
                        new Crawling(CrawlingType.NONE, null, null),
                        new Crawling(CrawlingType.NONE, null, null)
                ),
                List.of()
        ).validate()).isInstanceOf(SubscriptionRegisterFailureException.class);
    }

    @Test
    void LIST_CRAWLING_타입일경우_listCrawlings이_필요하다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new SubscriptionRegister(
                new TechContentProvider.Id(1),
                SubscriptionType.LIST_CRAWLING,
                SubscriptionInitType.LIST_CRAWLING,
                null,
                new ContentCrawling(
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
                ),
                List.of()
        ).validate()).isInstanceOf(SubscriptionRegisterFailureException.class);
    }

    @Test
    void LIST_CRAWLING_타입일경우_contentCrawling의_모든값이_필요하다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new SubscriptionRegister(
                new TechContentProvider.Id(1),
                SubscriptionType.LIST_CRAWLING,
                SubscriptionInitType.LIST_CRAWLING,
                null,
                new ContentCrawling(
                        new Crawling(CrawlingType.NONE, null, null),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3))
                ),
                List.of(new ListCrawling("list-url", new Crawling(CrawlingType.INDEX, null, List.of(1, 2, 3)), null))
        ).validate()).isInstanceOf(SubscriptionRegisterFailureException.class);
    }
}