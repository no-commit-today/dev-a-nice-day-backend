package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PromptEntityTest {

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        PromptEntity promptEntity = new PromptEntity(
                1L,
                PromptType.CATEGORIZE,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "v1",
                "model",
                "content"
        );

        // when
        final Prompt result = promptEntity.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new Prompt.Id(1L));
        assertThat(result.getType()).isEqualTo(PromptType.CATEGORIZE);
        assertThat(result.getProviderType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getVersion()).isEqualTo("v1");
        assertThat(result.getModel()).isEqualTo("model");
        assertThat(result.getContent()).isEqualTo("content");
    }
}