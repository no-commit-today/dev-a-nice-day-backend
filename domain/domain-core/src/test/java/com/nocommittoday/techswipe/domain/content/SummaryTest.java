package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SummaryTest {

    @Test
    void 내용이_형식에_맞는지_확인한다() {
        // given
        var content = """
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isTrue();
    }

    @Test
    void 내용은_2줄일_수_없다() {
        // given
        var content = """
                1. 요약 1
                2. 요약 2
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }

    @Test
    void 내용은_4줄일_수_없다() {
        // given
        var content = """
                1. 요약 1
                2. 요약 2
                3. 요약 3
                4. 요약 4
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }

    @Test
    void 내용은_2부터_시작할_수_없다() {
        // given
        final String content = """
                2. 요약 2
                2. 요약 2
                3. 요약 3
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }

    @Test
    void 내용은_한글이_포함되어야_한다() {
        // given
        final String content = """
                1. 요약 1
                2. 요약 2
                3. Summary 3
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }

    @Test
    void 요약_앞에_대답이_포함되지않고_요약만_포함되어야_한다() {
        // given
        var content = """
                네 알겠습니다. 내용을 요약하겠습니다.
                
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }

    @Test
    void 요약_뒤에_대답이_포함되지않고_요약만_포함되어야_한다() {
        // given
        var content = """
                1. 요약 1
                2. 요약 2
                3. 요약 3
                
                네 알겠습니다. 내용을 요약하겠습니다.
                """.trim();

        // when
        // then
        var summary = new Summary(content);
        assertThat(summary.isValid()).isFalse();
    }
}