package com.nocommittoday.techswipe.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PageParamTest {

    @Test
    void page_크기는_1_미만_일_수_없다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new PageParam(0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void size_크기는_1_미만_일_수_없다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new PageParam(1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void offset() {
        // given
        // when
        // then
        assertAll(
                () -> assertThat(new PageParam(1, 10).offset()).isEqualTo(0),
                () -> assertThat(new PageParam(2, 10).offset()).isEqualTo(10),
                () -> assertThat(new PageParam(3, 10).offset()).isEqualTo(20)
        );
    }
}