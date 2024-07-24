package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentCreate;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentEntityTest {

    @Test
    void TechContentCreate_도메인_모델로부터_생성할_수_있다() {
        // given
        final TechContentCreate domain = new TechContentCreate(
                new TechContentId(1),
                new TechContentProviderId(3),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                new ImageId(2),
                "summary",
                List.of(TechCategory.DEVOPS)
        );

        // when
        final TechContentEntity result = TechContentEntity.from(domain);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getProvider().getId()).isEqualTo(3);
        assertThat(result.getImage().getId()).isEqualTo(2);
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getCategories()).hasSize(1);
        assertThat(result.getCategories().get(0).getCategory()).isEqualTo(TechCategory.DEVOPS);
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
                        ImageEntity.from(new ImageId(4L))
                ),
                ImageEntity.from(new ImageId(2L)),
                "url",
                "title",
                "summary",
                LocalDate.of(2021, 1, 1)
        );
        content.addCategory(TechCategory.DEVOPS);

        // when
        final TechContent result = content.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new TechContentId(1));
        assertThat(result.getProvider().getId()).isEqualTo(new TechContentProviderId(3));
        assertThat(result.getProvider().getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getProvider().getTitle()).isEqualTo("providerTitle");
        assertThat(result.getProvider().getUrl()).isEqualTo("providerUrl");
        assertThat(result.getProvider().getIconId()).isEqualTo(new ImageId(4));
        assertThat(result.getImageId()).isEqualTo(new ImageId(2));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getCategories()).containsExactly(TechCategory.DEVOPS);
    }

}