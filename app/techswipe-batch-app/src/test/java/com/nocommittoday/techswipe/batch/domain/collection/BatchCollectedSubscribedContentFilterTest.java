package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.test.SubscribedContentBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BatchCollectedSubscribedContentFilterTest {

    @Test
    void 컨텐츠의_URL중_수집된_것이_없고_컨텐츠는_있으면_다음이_있는_것으로_간주한다() {
        // given
        BatchCollectedSubscribedContentFilter contentFilter = new BatchCollectedSubscribedContentFilter(
                Set.of(),
                List.of(
                        SubscribedContentBuilder.create(),
                        SubscribedContentBuilder.create(),
                        SubscribedContentBuilder.create()
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
                SubscribedContentBuilder.create(),
                SubscribedContentBuilder.create(),
                SubscribedContentBuilder.create()
        );
        BatchCollectedSubscribedContentFilter contentFilter = new BatchCollectedSubscribedContentFilter(
                Set.of(contents.get(0).getUrl()),
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
                SubscribedContentBuilder.create(),
                SubscribedContentBuilder.create(),
                SubscribedContentBuilder.create(),
                SubscribedContentBuilder.create()
        );
        BatchCollectedSubscribedContentFilter contentFilter = new BatchCollectedSubscribedContentFilter(
                Set.of(contents.get(3).getUrl(), contents.get(1).getUrl()),
                contents
        );

        // when
        List<SubscribedContent> filtered = contentFilter.filter();

        // then
        assertThat(filtered).hasSize(2);
        assertThat(filtered).containsExactly(contents.get(0), contents.get(2));
    }
}