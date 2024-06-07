package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectedContentEntityTest {

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        final CollectedContentEntity entity = new CollectedContentEntity(
                1L,
                CollectionType.RSS,
                CollectionStatus.CATEGORIZED,
                TechContentProviderEntity.from(new TechContentProvider.TechContentProviderId(2L)),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl",
                List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER),
                null
        );

        // when
        final CollectedContent result = entity.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new CollectedContent.CollectedContentId(1L));
        assertThat(result.getType()).isEqualTo(CollectionType.RSS);
        assertThat(result.getProviderId()).isEqualTo(new TechContentProvider.TechContentProviderId(2L));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
        assertThat(result.getCategories()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.SERVER);
    }

    @Test
    void ContentCollect_모델로부터_생성할_수_있다() {
        // given
        ContentCollect contentCollect = new ContentCollect(
                CollectionType.RSS,
                new TechContentProvider.TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        CollectedContentEntity result = CollectedContentEntity.from(contentCollect);

        // then
        assertThat(result.getId()).isNull();
        assertThat(result.getType()).isEqualTo(CollectionType.RSS);
        assertThat(result.getProvider().getId()).isEqualTo(2L);
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
        assertThat(result.getCategories()).isNull();
    }

}