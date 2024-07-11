package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderEntityTest {

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        final TechContentProvider.Id id = new TechContentProvider.Id(1);

        // when
        final TechContentProviderEntity result = TechContentProviderEntity.from(id);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        final TechContentProviderEntity provider = new TechContentProviderEntity(
                1L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                ImageEntity.from(new Image.Id(2L))
        );

        // when
        final TechContentProvider result = provider.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new TechContentProvider.Id(1));
        assertThat(result.getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getIconId()).isEqualTo(new Image.Id(2));
    }

}