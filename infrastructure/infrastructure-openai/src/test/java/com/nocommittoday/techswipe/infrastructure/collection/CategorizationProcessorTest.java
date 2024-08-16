package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.categoryList().getContent()).containsExactlyInAnyOrder(CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER);
        assertThat(result.exception()).isNull();
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
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.categoryList().getContent()).containsExactlyInAnyOrder(
                CollectionCategory.SW_ENGINEERING, CollectionCategory.SERVER, CollectionCategory.DEVOPS);
        assertThat(result.exception()).isNull();
    }

    @Test
    void 카테고리_수는_1개가_될_수_있다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn(String.format("""
                - %s
                """, CollectionCategory.SW_ENGINEERING).trim());

        // when
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.categoryList().getContent()).containsExactlyInAnyOrder(CollectionCategory.SW_ENGINEERING);
        assertThat(result.exception()).isNull();
    }

    @Test
    void 카테고리_수는_0개가_될_수_없다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        given(categorizationClient.categorize(collectedContent)).willReturn("");

        // when
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.exception()).isNotNull();
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
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.exception()).isInstanceOf(CategorizationResponseInvalidException.class);
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
        CategorizationResult result = categorizationProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.exception()).isInstanceOf(CategorizationResponseInvalidException.class);
    }
}