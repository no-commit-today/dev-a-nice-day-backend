package com.nocommittoday.techswipe.content.controller.v1.request;

import com.nocommittoday.techswipe.content.controller.v1.ContentListQueryRequest;
import com.nocommittoday.techswipe.content.domain.TechCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentListQueryRequestTest {

    @Test
    void categories가_null이면_TechCategory_모든_값으로_초기화된다() {
        // given
        final ContentListQueryRequest request = new ContentListQueryRequest(null);

        // then
        assertThat(request.categories())
                .containsExactlyInAnyOrder(TechCategory.values());
    }
}