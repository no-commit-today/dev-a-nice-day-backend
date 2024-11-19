package com.devniceday.batch.domain;

import com.devniceday.batch.domain.test.SubscriptionFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContentInitializerTest {

    private ContentInitializer contentInitializer;

    @Test
    void 지원하는_커맨드가_없을_경우_예외를_발생시킨다() {
        // given
        Subscription subscription = SubscriptionFixture.createFeed();
        ContentInitializeCommand command = mock(ContentInitializeCommand.class);
        given(command.supports(subscription)).willReturn(false);

        contentInitializer = new ContentInitializer(List.of(command));

        // when
        // then
        assertThatThrownBy(() -> contentInitializer.initialize(subscription, "url"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원하지 않는 구독 타입입니다. subscription=" + subscription);
    }

    @Test
    void 컨텐츠_초기화_데이터를_읽어온다() {
        // given
        Subscription subscription = SubscriptionFixture.createFeed();
        ContentInitializeCommand command = mock(ContentInitializeCommand.class);
        given(command.supports(subscription)).willReturn(true);
        ContentInitialization content = new ContentInitialization(
                "title",
                "image-url",
                LocalDate.of(2024, 6, 17),
                "content"
        );
        given(command.initialize(subscription, "url")).willReturn(content);

        contentInitializer = new ContentInitializer(List.of(command));

        // when
        ContentInitialization result = contentInitializer.initialize(subscription, "url");

        // then
        assertThat(result).isEqualTo(content);
    }
}