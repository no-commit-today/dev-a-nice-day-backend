package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderEntityTest {

    @Test
    void toDomain() {
        // given
        final TechContentProviderEntity provider = new TechContentProviderEntity(
                1L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new ImageIdEmbeddable(2L)
        );

        // when
        final TechContentProvider result = provider.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new TechContentProvider.TechContentProviderId(1));
        assertThat(result.getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getIconId()).isEqualTo(new Image.ImageId(2));
    }

}