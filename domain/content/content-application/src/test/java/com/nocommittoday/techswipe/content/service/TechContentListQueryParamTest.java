package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentListQueryParamTest {

    @Test
    void categories가_null이면_TechCategory_모든_값으로_초기화된다() {
        // given
        final TechContentListQueryParam request = new TechContentListQueryParam(null);

        // then
        assertThat(request.categories())
                .containsExactlyInAnyOrder(TechCategory.values());
    }
}