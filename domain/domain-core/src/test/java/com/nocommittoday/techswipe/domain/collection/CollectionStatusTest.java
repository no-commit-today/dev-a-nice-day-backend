package com.nocommittoday.techswipe.domain.collection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionStatusTest {

    @Test
    void 카테고리_분류가_가능한_상태() {
        // given
        // when
        // then
        assertThat(CollectionStatus.INIT.categorizable()).isTrue();
        assertThat(CollectionStatus.CATEGORIZATION_FAILED.categorizable()).isTrue();
    }

    @Test
    void 내용_요약이_가능한_상태() {
        // given
        // when
        // then
        assertThat(CollectionStatus.CATEGORIZED.summarizable()).isTrue();
        assertThat(CollectionStatus.SUMMARIZATION_FAILED.summarizable()).isTrue();
    }

    @Test
    void 발행이_가능한_상태() {
        // given
        // when
        // then
        assertThat(CollectionStatus.SUMMARIZED.publishable()).isTrue();
    }

    @Test
    void 실패_상태로_변환() {
        // given
        // when
        // then
        assertThat(CollectionStatus.COLLECTED.toFailed()).isEqualTo(CollectionStatus.INIT_FAILED);
        assertThat(CollectionStatus.INIT.toFailed()).isEqualTo(CollectionStatus.CATEGORIZATION_FAILED);
        assertThat(CollectionStatus.CATEGORIZED.toFailed()).isEqualTo(CollectionStatus.SUMMARIZATION_FAILED);
    }

}