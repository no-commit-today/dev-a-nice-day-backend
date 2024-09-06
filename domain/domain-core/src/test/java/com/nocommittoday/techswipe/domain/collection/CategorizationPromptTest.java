package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.test.CollectedContentBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategorizationPromptTest {

    @Test
    void CollectedContent를_통해서_생성한다() {
        // given
        CollectedContent content = CollectedContentBuilder.createInit();

        // when
        CategorizationPrompt prompt = CategorizationPrompt.of(content);

        // then
        assertThat(prompt).isNotNull();
        assertThat(prompt.getUser()).isEqualTo(content.getTitle() + "\n\n" + content.getContent());
    }

    @Test
    void CollectedContent가_분류_가능한_상태가_아닐경우_예외가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> CategorizationPrompt.of(CollectedContentBuilder.createCollected()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}