package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.CollectionType;
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
                CollectionType.FEED,
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
        assertThat(entity.getType()).isEqualTo(CollectionType.FEED);
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
                CollectionType.FEED,
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
        assertThat(result.getType()).isEqualTo(CollectionType.FEED);
        assertThat(result.getProviderId()).isEqualTo(new TechContentProvider.TechContentProviderId(2L));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
        assertThat(result.getCategories()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.SERVER);
    }

    @Test
    void 도메인_엔티티로부터_업데이트할_수_있다() {
        CollectedContentEntity entity = new CollectedContentEntity(
                1L,
                CollectionType.FEED,
                CollectionStatus.NONE,
                TechContentProviderEntity.from(new TechContentProvider.TechContentProviderId(2L)),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl",
                null,
                null
        );

        final CollectedContent domain = new CollectedContent(
                new CollectedContent.CollectedContentId(1L),
                CollectionType.LIST_CRAWLING,
                CollectionStatus.CATEGORIZED,
                List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER),
                "summary",
                new TechContentProvider.TechContentProviderId(3L),
                "updated-url",
                "updated-title",
                LocalDate.of(2021, 1, 2),
                "updated-content",
                "updated-imageUrl"
        );

        // when
        entity.update(domain);

        // then
        assertThat(entity.getType()).isEqualTo(CollectionType.LIST_CRAWLING);
        assertThat(entity.getStatus()).isEqualTo(CollectionStatus.CATEGORIZED);
        assertThat(entity.getCategories()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.SERVER);
        assertThat(entity.getSummary()).isEqualTo("summary");
        assertThat(entity.getProvider().getId()).isEqualTo(3L);
        assertThat(entity.getUrl()).isEqualTo("updated-url");
        assertThat(entity.getTitle()).isEqualTo("updated-title");
        assertThat(entity.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 2));
        assertThat(entity.getContent()).isEqualTo("updated-content");
        assertThat(entity.getImageUrl()).isEqualTo("updated-imageUrl");

    }

}