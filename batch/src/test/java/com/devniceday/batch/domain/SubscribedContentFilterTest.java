package com.devniceday.batch.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SubscribedContentFilterTest {

    @Test
    void 컨텐츠의_URL중_수집된_것이_없고_컨텐츠는_있으면_다음이_있는_것으로_간주한다() {
        // given
        SubscribedContentFilter contentFilter = new SubscribedContentFilter(
                Set.of(),
                List.of(
                        new SubscribedContent(
                                1,
                                "url-1",
                                "title-1",
                                "image-url-1",
                                LocalDate.of(2021, 1, 1),
                                "content-1"
                        ),
                        new SubscribedContent(
                                2,
                                "url-2",
                                "title-2",
                                "image-url-2",
                                LocalDate.of(2021, 1, 2),
                                "content-2"
                        ),
                        new SubscribedContent(
                                3,
                                "url-3",
                                "title-3",
                                "image-url-3",
                                LocalDate.of(2021, 1, 3),
                                "content-3"
                        )
                )
        );

        // when
        boolean hasNext = contentFilter.hasNext();

        // then
        assertThat(hasNext).isTrue();
    }

    @Test
    void 컨텐츠의_URL중_수집된_것이_있으면_다음이_없는_것으로_간주한다() {
        // given
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
                        2,
                        "url-2",
                        "title-2",
                        "image-url-2",
                        LocalDate.of(2021, 1, 2),
                        "content-2"
                ),
                new SubscribedContent(
                        3,
                        "url-3",
                        "title-3",
                        "image-url-3",
                        LocalDate.of(2021, 1, 3),
                        "content-3"
                )
        );
        SubscribedContentFilter contentFilter = new SubscribedContentFilter(
                Set.of(contents.get(0).url()),
                contents
        );

        // when
        boolean hasNext = contentFilter.hasNext();

        // then
        assertThat(hasNext).isFalse();
    }

    @Test
    void 컨텐츠의_URL중_수집된_것이_있으면_필터링_된다() {
        // given
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
                        2,
                        "url-2",
                        "title-2",
                        "image-url-2",
                        LocalDate.of(2021, 1, 2),
                        "content-2"
                ),
                new SubscribedContent(
                        3,
                        "url-3",
                        "title-3",
                        "image-url-3",
                        LocalDate.of(2021, 1, 3),
                        "content-3"
                ),
                new SubscribedContent(
                        4,
                        "url-4",
                        "title-4",
                        "image-url-4",
                        LocalDate.of(2021, 1, 4),
                        "content-4"
                )
        );
        SubscribedContentFilter contentFilter = new SubscribedContentFilter(
                Set.of(contents.get(3).url(), contents.get(1).url()),
                contents
        );

        // when
        List<SubscribedContent> filtered = contentFilter.filter();

        // then
        assertThat(filtered)
                .hasSize(2)
                .containsExactly(contents.get(0), contents.get(2));
    }
}