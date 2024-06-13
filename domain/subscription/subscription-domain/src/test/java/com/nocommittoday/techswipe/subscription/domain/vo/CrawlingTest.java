package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrawlingTest {

    @Test
    void NONE_타입으로_생성할_수_있다() {
        // given
        // when
        final Crawling result = Crawling.ofNone();

        // then
        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(CrawlingType.NONE);
        assertThat(result.selector()).isNull();
        assertThat(result.indexes()).isNull();
    }

    @Test
    void INDEX_타입으로_생성할_수_있다() {
        // given
        final var indexes = List.of(1, 2, 3);

        // when
        final Crawling result = Crawling.ofIndex(indexes);

        // then
        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(CrawlingType.INDEX);
        assertThat(result.selector()).isNull();
        assertThat(result.indexes()).isEqualTo(indexes);
    }

    @Test
    void INDEX_타입으로_생성할_때_indexes가_null이면_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Crawling.ofIndex(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void INDEX_타입으로_생성할_때_indexes가_empty이면_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Crawling.ofIndex(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void SELECTOR_타입으로_생성할_수_있다() {
        // given
        final var selector = "selector";

        // when
        final Crawling result = Crawling.ofSelector(selector);

        // then
        assertThat(result).isNotNull();
        assertThat(result.type()).isEqualTo(CrawlingType.SELECTOR);
        assertThat(result.selector()).isEqualTo(selector);
        assertThat(result.indexes()).isNull();
    }

    @Test
    void SELECTOR_타입으로_생성할_때_selector가_null이면_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Crawling.ofSelector(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void SELECTOR_타입으로_생성할_때_selector가_empty이면_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Crawling.ofSelector(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

}