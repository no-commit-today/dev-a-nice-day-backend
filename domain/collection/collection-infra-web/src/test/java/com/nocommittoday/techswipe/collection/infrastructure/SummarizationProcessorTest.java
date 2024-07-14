package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SummarizationProcessorTest {

    @InjectMocks
    private SummarizationProcessor summarizationProcessor;

    @Mock
    private SummarizationClient summarizationClient;

    @Test
    void 내용을_형식에_맞게_반환한다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.summary()).isEqualTo("""
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim());
    }

    @Test
    void 내용은_2줄일_수_없다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                1. 요약 1
                2. 요약 2
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.summary()).isNull();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
    }

    @Test
    void 내용은_4줄일_수_없다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                1. 요약 1
                2. 요약 2
                3. 요약 3
                4. 요약 4
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.summary()).isNull();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
    }

    @Test
    void 내용은_2부터_시작할_수_없다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                2. 요약 2
                3. 요약 3
                3. 요약 3
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.summary()).isNull();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
    }

    @Test
    void 내용은_한글이_포함되어야_한다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                1. Summary 1
                2. Summary 2
                3. Summary 3
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.summary()).isNull();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
    }

    @Test
    void 대답이_포함되지않고_요약만_포함되어야_한다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        given(summarizationClient.summarize(collectedContent)).willReturn("""
                네 알겠습니다. 내용을 요약하겠습니다.
                
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim());

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.summary()).isNull();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
    }
}