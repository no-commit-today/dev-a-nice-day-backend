package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderQueryTest {

    @Test
    void 게터() {
        // given
        TechContentProviderQuery provider = new TechContentProviderQuery(
                new TechContentProviderId(1),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                "iconUrl"
        );

        // when
        // then
        assertThat(provider.getId().value()).isEqualTo(1);
        assertThat(provider.getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(provider.getTitle()).isEqualTo("title");
        assertThat(provider.getUrl()).isEqualTo("url");
        assertThat(provider.getIconUrl()).isEqualTo("iconUrl");
    }
}