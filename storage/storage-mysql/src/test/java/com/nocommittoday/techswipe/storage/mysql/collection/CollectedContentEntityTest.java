package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CollectedContentEntityTest {

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        CollectedContentEntity entity = new CollectedContentEntity(
                1L,
                CollectionStatus.CATEGORIZED,
                TechContentProviderEntity.from(new TechContentProviderId(2L)),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl",
                List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER),
                null
        );

        // when
        CollectedContent result = entity.toDomain();

        // then
        assertThat(result.getId()).isEqualTo(new CollectedContentId(1L));
        assertThat(result.getProviderId()).isEqualTo(new TechContentProviderId(2L));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
        assertThat(Objects.requireNonNull(result.getCategoryList()).getContent()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.SERVER);
    }

    @Test
    void 도메인_엔티티로부터_업데이트할_수_있다() {
        CollectedContentEntity entity = new CollectedContentEntity(
                1L,
                CollectionStatus.INIT,
                TechContentProviderEntity.from(new TechContentProviderId(2L)),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl",
                null,
                null
        );

        Summary summary = SummaryBuilder.create();
        CollectedContent domain = new CollectedContent(
                new CollectedContentId(1L),
                CollectionStatus.CATEGORIZED,
                CollectionCategoryList.create(List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER)),
                summary,
                new TechContentProviderId(3L),
                "updated-url",
                "updated-title",
                LocalDate.of(2021, 1, 2),
                "updated-content",
                "updated-imageUrl"
        );

        // when
        entity.update(domain);

        // then
        assertThat(entity.getStatus()).isEqualTo(CollectionStatus.CATEGORIZED);
        assertThat(entity.getCategories()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.SERVER);
        assertThat(entity.getSummary()).isEqualTo(summary.getContent());
        assertThat(entity.getProvider().getId()).isEqualTo(3L);
        assertThat(entity.getUrl()).isEqualTo("updated-url");
        assertThat(entity.getTitle()).isEqualTo("updated-title");
        assertThat(entity.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 2));
        assertThat(entity.getContent()).isEqualTo("updated-content");
        assertThat(entity.getImageUrl()).isEqualTo("updated-imageUrl");

    }

}