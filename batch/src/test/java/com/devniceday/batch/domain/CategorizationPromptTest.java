package com.devniceday.batch.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategorizationPromptTest {

    @Test
    void title_content를_통해서_생성한다() {
        // given
        // when
        CategorizationPrompt prompt = CategorizationPrompt.of("컨텐츠 제목", "컨텐츠 내용");

        // then
        assertThat(prompt).isNotNull();
        assertThat(prompt.getUser()).isEqualTo("컨텐츠 제목\n\n컨텐츠 내용");
    }
}