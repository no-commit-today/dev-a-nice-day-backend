package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderTest {

    @Test
    void 게터() {
        // given
        TechContentProvider provider = new TechContentProvider(
                new TechContentProviderId(1),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new ImageId(2)
        );

        // when
        // then
        assertThat(provider.getId().value()).isEqualTo(1);
        assertThat(provider.getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(provider.getTitle()).isEqualTo("title");
        assertThat(provider.getUrl()).isEqualTo("url");
        assertThat(provider.getIconId().value()).isEqualTo(2);
    }
}