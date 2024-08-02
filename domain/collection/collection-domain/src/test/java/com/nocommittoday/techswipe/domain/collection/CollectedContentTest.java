package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CollectedContentTest {

    @Test
    void 카테고리가_분류되면_status_가_CATEGORIZED_로_변경된다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
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
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
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

    @Test
    void NONE_상태가_아닐_경우_카테고리를_분류할_수_없다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        ).categorize(List.of(CollectionCategory.DEVOPS));

        // when
        // then
        assertThatThrownBy(() -> collectedContent.categorize(List.of(CollectionCategory.SERVER)))
                .isInstanceOf(CollectionCategorizeUnableException.class);
    }

    @Test
    void 카테고리가_분류된_후_내용_요약을_추가하면_status_가_SUMMARIZED_로_변경된다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        CollectedContent categorized = collectedContent.categorize(
                List.of(CollectionCategory.DEVOPS)
        );

        // when
        CollectedContent result = categorized.summarize("summary");

        // then
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.SUMMARIZED);
    }

    @Test
    void 카테고리가_분류되기_전에_내용_요약을_추가할_수_없다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        // then
        assertThatThrownBy(() -> collectedContent.summarize("summary"))
                .isInstanceOf(CollectionSummarizeUnableException.class);
    }

    @Test
    void 카테고리_분류에서_필터링되면_내용_요약을_추가할_수_없다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        CollectedContent filtered = collectedContent.categorize(
                List.of(CollectionCategory.NON_DEV)
        );

        // when
        // then
        assertThatThrownBy(() -> filtered.summarize("summary"))
                .isInstanceOf(CollectionSummarizeUnableException.class);
    }

    @Test
    void 발행_완료로_상태를_변경할_수_있다() {
        // given
        CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        CollectedContent categorized = collectedContent.categorize(
                List.of(CollectionCategory.DEVOPS)
        );

        CollectedContent summarized = categorized.summarize("summary");

        // when
        CollectedContent result = summarized.published();

        // then
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.PUBLISHED);

        assertThat(result.getId()).isEqualTo(new CollectedContentId(1L));
        assertThat(result.getProviderId()).isEqualTo(new TechContentProviderId(2L));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
        assertThat(result.getCategories()).containsExactly(CollectionCategory.DEVOPS);
        assertThat(result.getSummary()).isEqualTo("summary");
    }

    @Test
    void 요약_완료된_상태가_아니면_발행_완료_상태로_변경할_수_없다() {
        // given
        CollectedContent none = new CollectedContent(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        CollectedContent categorized = none.categorize(
                List.of(CollectionCategory.DEVOPS)
        );

        // when
        // then
        assertAll(
                () -> assertThatThrownBy(() -> none.published())
                        .isInstanceOf(CollectionPublishUnableException.class),
                () -> assertThatThrownBy(() -> categorized.published())
                        .isInstanceOf(CollectionPublishUnableException.class)
        );
    }
}