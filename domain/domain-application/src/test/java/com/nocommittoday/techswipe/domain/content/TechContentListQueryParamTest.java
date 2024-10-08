package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentListQueryParamTest {

    @Test
    void categories가_null이면_TechCategory_모든_값으로_초기화된다() {
        // given
        TechContentListQueryParam request = new TechContentListQueryParam(null);

        // then
        assertThat(request.categories())
                .containsExactlyInAnyOrder(TechCategory.values());
    }
}