package com.nocommittoday.techswipe.collection.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContentCategoryEditTest {

    @Test
    void 변경할_카테고리_리스트는_null일_수_업다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new ContentCategoryEdit(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void 변경할_카테고리는_1개_이상이어야_한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new ContentCategoryEdit(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 카테고리_변경_가능한_상태() {
        // given
        final ContentCategoryEdit contentCategoryEdit = new ContentCategoryEdit(
                List.of(CollectionCategory.DEVOPS));

        // when
        // then
        assertThat(contentCategoryEdit.isEditable(CollectionStatus.FILTERED)).isTrue();
        assertThat(contentCategoryEdit.isEditable(CollectionStatus.PUBLISHED)).isTrue();

        Arrays.stream(CollectionStatus.values())
                .filter(status -> status != CollectionStatus.FILTERED)
                .filter(status -> status != CollectionStatus.PUBLISHED)
                .forEach(status -> assertThat(contentCategoryEdit.isEditable(status)).isFalse());
    }

    @Test
    void 사용되지않는_카테고리가_있을경우_FILTERED_상태로_변경된다() {
        // given
        final ContentCategoryEdit contentCategoryEdit = new ContentCategoryEdit(
                List.of(CollectionCategory.DEVOPS, CollectionCategory.NON_DEV));

        // when

        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(collectedContent.getStatus()).willReturn(CollectionStatus.PUBLISHED);
        final CollectionStatus result = contentCategoryEdit.nextContentStatus(
                collectedContent
        );

        // then
        assertThat(result).isEqualTo(CollectionStatus.FILTERED);
    }

    @Test
    void PUBLISHED_상태인_경우_사용되지않는_카테고리가_없으면_PUBLISHED_상태가_유지된다() {
        // given
        final ContentCategoryEdit contentCategoryEdit = new ContentCategoryEdit(
                List.of(CollectionCategory.DEVOPS));

        // when
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(collectedContent.getStatus()).willReturn(CollectionStatus.PUBLISHED);
        final CollectionStatus result = contentCategoryEdit.nextContentStatus(
                collectedContent
        );

        // then
        assertThat(result).isEqualTo(CollectionStatus.PUBLISHED);
    }

    @Test
    void FILTERED_상태인_경우_사용되지않는_카테고리가_없으면_CATEGORIZED_상태가_된다() {
        // given
        final ContentCategoryEdit contentCategoryEdit = new ContentCategoryEdit(
                List.of(CollectionCategory.DEVOPS));

        // when
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(collectedContent.getStatus()).willReturn(CollectionStatus.FILTERED);
        final CollectionStatus result = contentCategoryEdit.nextContentStatus(
                collectedContent
        );

        // then
        assertThat(result).isEqualTo(CollectionStatus.CATEGORIZED);
    }

}