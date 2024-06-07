package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectedContentEntityTest {

    @Test
    void 도메인_엔티티로부터_생성할_수_있다() {
        // given
        final CollectedContent content = new CollectedContent(
                new CollectedContent.CollectedContentId(1L),
                CollectionType.RSS,
                CollectionStatus.NONE,
                List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER),
                "summary",
                new TechContentProvider.TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"

        );

        // when
        final CollectedContentEntity entity = CollectedContentEntity.from(content);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getType()).isEqualTo(CollectionType.RSS);
        assertThat(entity.getStatus()).isEqualTo(CollectionStatus.NONE);
        assertThat(entity.getCategories()).isEqualTo(List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER));
        assertThat(entity.getSummary()).isEqualTo("summary");
        assertThat(entity.getProvider().getId()).isEqualTo(2L);
        assertThat(entity.getUrl()).isEqualTo("url");
        assertThat(entity.getTitle()).isEqualTo("title");
        assertThat(entity.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entity.getContent()).isEqualTo("content");
        assertThat(entity.getImageUrl()).isEqualTo("imageUrl");
    }

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

}