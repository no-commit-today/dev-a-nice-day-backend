package com.devniceday.batch.domain;

import com.devniceday.batch.domain.test.SubscribedContentFixture;
import com.devniceday.batch.domain.test.SubscriptionFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SubscribedContentFetcherTest {

    private SubscribedContentFetcher subscribedContentFetcher;

    @Test
    void 지원하는_커맨드가_없을_경우_예외를_발생시킨다() {
        // given
        Subscription subscription = SubscriptionFixture.createFeed();
        SubscribedContentFetchCommand command = mock(SubscribedContentFetchCommand.class);
        given(command.supports(subscription)).willReturn(false);

        subscribedContentFetcher = new SubscribedContentFetcher(List.of(command));

        // when
        // then
        assertThatThrownBy(() -> subscribedContentFetcher.fetch(subscription, 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원하지 않는 구독 타입입니다. subscription=" + subscription);
    }

    @Test
    void 구독_컨텐츠_데이터를_읽어온다() {
        // given
        Subscription subscription = SubscriptionFixture.createFeed();
        SubscribedContentFetchCommand command = mock(SubscribedContentFetchCommand.class);
        given(command.supports(subscription)).willReturn(true);
        SubscribedContent content = SubscribedContentFixture.create();
        given(command.fetch(subscription, 1)).willReturn(List.of(content));

        subscribedContentFetcher = new SubscribedContentFetcher(List.of(command));

        // when
        List<SubscribedContent> result = subscribedContentFetcher.fetch(subscription, 1);

        // then
        assertThat(result).containsExactly(content);
    }
}