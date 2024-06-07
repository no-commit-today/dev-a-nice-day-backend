package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectedContentTest {

    @Test
    void 카테고리가_분류되면_status_가_CATEGORIZED_로_변경된다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContent.CollectedContentId(1L),
                CollectionType.RSS,
                new TechContentProvider.TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        CollectedContent result = collectedContent.categorize(List.of(CollectionCategory.DEVOPS));

        // then
        assertThat(result.getCategories()).containsExactly(CollectionCategory.DEVOPS);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.CATEGORIZED);
    }

    @Test
    void 분류된_카테고리중_사용되지_않는_카테고리가_있을_경우_status_가_FILTERED_로_변경된다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContent.CollectedContentId(1L),
                CollectionType.RSS,
                new TechContentProvider.TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        CollectedContent result = collectedContent.categorize(
                List.of(CollectionCategory.DEVOPS, CollectionCategory.NON_DEV)
        );

        // then
        assertThat(result.getCategories()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.NON_DEV);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.FILTERED);
    }
}