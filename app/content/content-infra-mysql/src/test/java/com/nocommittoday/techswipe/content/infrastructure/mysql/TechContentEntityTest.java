package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TechContentEntityTest {

    @Test
    void toDomain() {
        // given
        final TechContentEntity content = new TechContentEntity(
                1L,
                new TechContentProviderEntity(
                        3L,
                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                        "providerTitle",
                        "providerUrl",
                        new ImageIdEmbeddable(4L)
                ),
                new ImageIdEmbeddable(2L),
                "url",
                "title",
                "content",
                "summary",
                LocalDate.of(2021, 1, 1),
                List.of(new TechCategoryEntity(5L, mock(TechContentEntity.class), TechCategory.INFRA)
        ));

        // when
        final TechContent result = content.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new TechContent.TechContentId(1));
        assertThat(result.getProvider().getId()).isEqualTo(new TechContentProvider.TechContentProviderId(3));
        assertThat(result.getProvider().getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getProvider().getTitle()).isEqualTo("providerTitle");
        assertThat(result.getProvider().getUrl()).isEqualTo("providerUrl");
        assertThat(result.getProvider().getIconId()).isEqualTo(new Image.ImageId(4));
        assertThat(result.getImageId()).isEqualTo(new Image.ImageId(2));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getCategories()).containsExactly(TechCategory.INFRA);
    }

}