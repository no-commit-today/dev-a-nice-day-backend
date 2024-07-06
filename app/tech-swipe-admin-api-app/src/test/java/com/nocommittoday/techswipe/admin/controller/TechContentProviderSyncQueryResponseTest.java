package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderSyncQueryResponseTest {

    @Test
    void 도메인_엔티티로부터_생성할_수_있다() {
        // given
        final TechContentProvider provider = new TechContentProvider(
                new TechContentProvider.Id(1L),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new Image.Id(2L)
        );

        // when
        final TechContentProviderSyncQueryResponse response = TechContentProviderSyncQueryResponse.from(provider);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.type()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(response.title()).isEqualTo("title");
        assertThat(response.url()).isEqualTo("url");
        assertThat(response.iconId()).isEqualTo(2L);
    }
}