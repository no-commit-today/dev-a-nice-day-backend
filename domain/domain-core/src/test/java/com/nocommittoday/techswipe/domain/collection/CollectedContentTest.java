package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.test.CollectedContentBuilder;
import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CollectedContentTest {

    @Test
    void 카테고리가_분류되면_status_가_CATEGORIZED_로_변경된다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createInit();

        // when
        CollectedContent result = collectedContent.categorize(
                CollectionCategoryList.create(List.of(CollectionCategory.DEVOPS)));

        // then
        assertThat(Objects.requireNonNull(result.getCategoryList()).getContent())
                .containsExactly(CollectionCategory.DEVOPS);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.CATEGORIZED);
    }

    @Test
    void 분류된_카테고리중_사용되지_않는_카테고리가_있을_경우_status_가_FILTERED_로_변경된다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createInit();

        // when
        CollectedContent result = collectedContent.categorize(
                CollectionCategoryList.create(List.of(CollectionCategory.DEVOPS, CollectionCategory.NON_DEV))
        );

        // then
        assertThat(Objects.requireNonNull(result.getCategoryList()).getContent()).containsExactly(CollectionCategory.DEVOPS, CollectionCategory.NON_DEV);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.FILTERED);
    }

    @Test
    void INIT_상태가_아닐_경우_카테고리를_분류할_수_없다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createCategorized();

        // when
        // then
        assertThatThrownBy(() -> collectedContent.categorize(
                CollectionCategoryList.create(List.of(CollectionCategory.SERVER)))
        ).isInstanceOf(CollectionCategorizeUnableException.class);
    }

    @Test
    void 카테고리가_분류된_후_내용_요약을_추가하면_status_가_SUMMARIZED_로_변경된다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createCategorized();

        // when
        Summary summary = SummaryBuilder.create();
        CollectedContent result = collectedContent.summarize(summary);

        // then
        assertThat(result.getSummary()).isEqualTo(summary);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.SUMMARIZED);
    }

    @Test
    void 카테고리가_분류되기_전에_내용_요약을_추가할_수_없다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createInit();

        // when
        // then
        assertThatThrownBy(() -> collectedContent.summarize(SummaryBuilder.create()))
                .isInstanceOf(CollectionSummarizeUnableException.class);
    }

    @Test
    void 카테고리_분류에서_필터링되면_내용_요약을_추가할_수_없다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createInit();

        CollectedContent filtered = collectedContent.categorize(
                CollectionCategoryList.create(List.of(CollectionCategory.NON_DEV))
        );

        // when
        // then
        assertThatThrownBy(() -> filtered.summarize(SummaryBuilder.create()))
                .isInstanceOf(CollectionSummarizeUnableException.class);
    }

    @Test
    void 발행_완료로_상태를_변경할_수_있다() {
        // given
        CollectedContent collectedContent = CollectedContentBuilder.createSummarized();

        // when
        CollectedContent result = collectedContent.published();

        // then
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.PUBLISHED);

        assertThat(result.getId()).isEqualTo(collectedContent.getId());
        assertThat(result.getProviderId()).isEqualTo(collectedContent.getProviderId());
        assertThat(result.getUrl()).isEqualTo(collectedContent.getUrl());
        assertThat(result.getTitle()).isEqualTo(collectedContent.getTitle());
        assertThat(result.getPublishedDate()).isEqualTo(collectedContent.getPublishedDate());
        assertThat(result.getContent()).isEqualTo(collectedContent.getContent());
        assertThat(result.getImageUrl()).isEqualTo(collectedContent.getImageUrl());
        assertThat(result.getCategoryList()).isEqualTo(collectedContent.getCategoryList());
        assertThat(result.getSummary()).isEqualTo(collectedContent.getSummary());
    }

    @Test
    void 요약_완료된_상태가_아니면_발행_완료_상태로_변경할_수_없다() {
        // give
        // when
        // then
        assertAll(
                () -> assertThatThrownBy(() -> CollectedContentBuilder.createCollected().published())
                        .isInstanceOf(CollectionPublishUnableException.class),
                () -> assertThatThrownBy(() -> CollectedContentBuilder.createInit().published())
                        .isInstanceOf(CollectionPublishUnableException.class),
                () -> assertThatThrownBy(() -> CollectedContentBuilder.createCategorized().published())
                        .isInstanceOf(CollectionPublishUnableException.class)
        );
    }
}