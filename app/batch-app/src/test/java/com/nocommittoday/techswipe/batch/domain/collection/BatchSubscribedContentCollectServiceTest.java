package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.batch.domain.subscription.BatchSubscribedContentReaderDelegator;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.test.FeedSubscriptionBuilder;
import com.nocommittoday.techswipe.domain.test.SubscribedContentBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BatchSubscribedContentCollectServiceTest {

    @InjectMocks
    private BatchSubscribedContentCollectService batchSubscribedContentCollectService;

    @Mock
    private BatchSubscribedContentReaderDelegator contentReader;

    @Mock
    private BatchCollectedSubscribedContentFilterCreator filterCreator;

    @Test
    void 최대_페이지에_도달할_때까지_구독_컨텐츠를_수집한다() {
        // given
        Subscription subscription = FeedSubscriptionBuilder.create();
        List<SubscribedContent> allCotents = new ArrayList<>();
        for (int page = 1; page <= 10; page++) {
            List<SubscribedContent> contents = List.of(SubscribedContentBuilder.create());
            allCotents.addAll(contents);
            given(contentReader.getList(subscription, page))
                    .willReturn(contents);
            given(filterCreator.create(contents))
                    .willReturn(new BatchCollectedSubscribedContentFilter(Set.of(), contents));
        }

        // when
        List<SubscribedContent> result = batchSubscribedContentCollectService
                .getList(subscription);

        // then
        assertThat(result)
                .hasSize(10)
                .isEqualTo(allCotents.subList(0, 10));
    }

    @Test
    void 빈_컨텐츠_리스트를_반환하는_경우_수집을_중단한다() {
        // given
        Subscription subscription = FeedSubscriptionBuilder.create();
        List<SubscribedContent> contents = List.of(SubscribedContentBuilder.create());
        given(contentReader.getList(subscription, 1))
                .willReturn(contents);
        given(filterCreator.create(contents))
                .willReturn(new BatchCollectedSubscribedContentFilter(Set.of(), contents));

        given(contentReader.getList(subscription, 2))
                .willReturn(List.of());

        // when
        List<SubscribedContent> result = batchSubscribedContentCollectService
                .getList(subscription);

        // then
        assertThat(result)
                .hasSize(1)
                .isEqualTo(contents);
    }

    @Test
    void 컨텐츠_필터에_수집된_컨텐츠가_존재할_경우_수집을_중단한다() {
        // given
        Subscription subscription = FeedSubscriptionBuilder.create();
        List<SubscribedContent> contents = List.of(SubscribedContentBuilder.create());
        given(contentReader.getList(subscription, 1))
                .willReturn(contents);
        given(filterCreator.create(contents))
                .willReturn(new BatchCollectedSubscribedContentFilter(Set.of(contents.get(0).getUrl()), contents));

        // when
        List<SubscribedContent> result = batchSubscribedContentCollectService
                .getList(subscription);

        // then
        assertThat(result)
                .isEmpty();
    }
}