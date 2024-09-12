package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CategorizationProcessorTest {

    @InjectMocks
    private CategorizationProcessor categorizationProcessor;

    @Mock
    private CategorizationClient categorizationClient;

    @Test
    void 내용을_형식에_맞게_반환한다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                - %s
                - %s
                """, CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER).trim());

        // when
        CollectionCategoryList result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.getContent()).containsExactlyInAnyOrder(CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER);
    }

    @Test
    void 카테고리_수는_3개가_될_수_있다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                - %s
                - %s
                - %s
                """, CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER, CollectionCategory.DEVOPS).trim());

        // when
        CollectionCategoryList result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.getContent()).containsExactlyInAnyOrder(
                CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER, CollectionCategory.DEVOPS);
    }

    @Test
    void 카테고리_수는_1개가_될_수_있다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                - %s
                """, CollectionCategory.SW_ENGINEERING).trim());

        // when
        CollectionCategoryList result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.getContent()).containsExactlyInAnyOrder(CollectionCategory.SW_ENGINEERING);
    }

    @Test
    void 카테고리_수는_0개가_될_수_없다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn("");

        // when
        // then
        assertThatThrownBy(() -> categorizationProcessor.categorize(collectedContent))
                .isInstanceOf(DomainValidationException.class);

    }

    @Test
    void 잘못된_카테고리가_포함될_수_없다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                - %s
                - %s
                """, CollectionCategory.SW_ENGINEERING, "INVALID").trim());

        // when
        // then
        assertThatThrownBy(() -> categorizationProcessor.categorize(collectedContent))
                .isInstanceOf(DomainValidationException.class);
    }

    @Test
    void 카테고리_이외의_내용이_포함될_수_없다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                네 알겠습니다. 내용을 분류하겠습니다.
                
                - %s
                - %s
                """, CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER).trim());

        // when
        assertThatThrownBy(() -> categorizationProcessor.categorize(collectedContent))
                .isInstanceOf(DomainValidationException.class);
    }
}