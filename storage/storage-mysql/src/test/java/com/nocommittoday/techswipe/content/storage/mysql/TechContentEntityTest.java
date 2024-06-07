package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentEntityTest {

    @Test
    void 도메인_엔티티_ID로_전환할_수_있다() {
        // given
        final TechContentEntity content = new TechContentEntity(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // when
        final TechContent.TechContentId result = content.toDomainId();

        // then
        assertThat(result).isEqualTo(new TechContent.TechContentId(1));
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        final TechContentEntity content = new TechContentEntity(
                1L,
                new TechContentProviderEntity(
                        3L,
                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                        "providerTitle",
                        "providerUrl",
                        ImageEntity.from(new Image.ImageId(4L))
                ),
                ImageEntity.from(new Image.ImageId(2L)),
                "url",
                "title",
                "summary",
                LocalDate.of(2021, 1, 1),
                List.of(new TechCategoryEntity(null, null, TechCategory.DEVOPS)
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
        assertThat(result.getCategories()).containsExactly(TechCategory.DEVOPS);
    }

}