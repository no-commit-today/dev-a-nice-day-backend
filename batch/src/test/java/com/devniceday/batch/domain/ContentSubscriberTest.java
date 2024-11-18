package com.devniceday.batch.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ContentSubscriberTest {

    @InjectMocks
    private ContentSubscriber contentSubscriber;

    @Mock
    private SubscribedContentFetcher contentFetcher;

    @Mock
    private SubscribedContentFilterFactory filterCreator;

    @Test
    void 최대_페이지에_도달할_때까지_구독_컨텐츠를_수집한다() {
        // given
        Subscription subscription = mock(Subscription.class);
        List<SubscribedContent> allCotents = new ArrayList<>();
        for (int page = 1; page <= 10; page++) {
            List<SubscribedContent> contents = List.of(new SubscribedContent(
                    1,
                    "url-" + page,
                    "title-" + page,
                    "image-url-" + page,
                    LocalDate.of(2021, 1, 1),
                    "content-" + page
            ));
            allCotents.addAll(contents);
            given(contentFetcher.fetch(subscription, page))
                    .willReturn(contents);
            given(filterCreator.create(contents))
                    .willReturn(new SubscribedContentFilter(Set.of(), contents));
        }

        // when
        List<SubscribedContent> result = contentSubscriber
                .subscribe(subscription);

        // then
        assertThat(result)
                .hasSize(10)
                .isEqualTo(allCotents.subList(0, 10));
    }

    @Test
    void 빈_컨텐츠_리스트를_반환하는_경우_수집을_중단한다() {
        // given
        Subscription subscription = mock(Subscription.class);
        List<SubscribedContent> contents = List.of();
        given(contentFetcher.fetch(subscription, 1))
                .willReturn(contents);
        given(filterCreator.create(contents))
                .willReturn(new SubscribedContentFilter(Set.of(), contents));

        // when
        List<SubscribedContent> result = contentSubscriber
                .subscribe(subscription);

        // then
        assertThat(result)
                .hasSize(0)
                .isEqualTo(contents);
    }

    @Test
    void 컨텐츠_필터에_수집된_컨텐츠가_존재할_경우_수집을_중단한다() {
        // given
        Subscription subscription = mock(Subscription.class);
        List<SubscribedContent> contents = List.of(
                new SubscribedContent(
                        1,
                        "url-1",
                        "title-1",
                        "image-url-1",
                        LocalDate.of(2021, 1, 1),
                        "content-1"
                ),
                new SubscribedContent(
                        1,
                        "url-2",
                        "title-2",
                        "image-url-2",
                        LocalDate.of(2021, 1, 1),
                        "content-2"
                )
        );
        given(contentFetcher.fetch(subscription, 1))
                .willReturn(contents);
        given(filterCreator.create(contents))
                .willReturn(new SubscribedContentFilter(Set.of(contents.get(0).url()), contents));

        // when
        List<SubscribedContent> result = contentSubscriber
                .subscribe(subscription);

        // then
        assertThat(result)
                .hasSize(1)
                .containsOnly(contents.get(1));
    }
}