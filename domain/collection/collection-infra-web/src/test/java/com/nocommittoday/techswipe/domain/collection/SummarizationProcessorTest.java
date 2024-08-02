package com.nocommittoday.techswipe.domain.collection;

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

    @Mock
    private SummarizationValidator summarizationValidator;

    @Test
    void 내용을_형식에_맞게_반환한다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        final String responseContent = """
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim();
        given(summarizationClient.summarize(collectedContent)).willReturn(responseContent);
        given(summarizationValidator.check(responseContent)).willReturn(true);


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
    void 내용이_형식에_맞지_않을_경우_예외를_발생시킨다() {
        // given
        final CollectedContent collectedContent = mock(CollectedContent.class);
        final String responseContent = """
                1. 요약 1
                2. 요약 2
                """.trim();
        given(summarizationClient.summarize(collectedContent)).willReturn(responseContent);
        given(summarizationValidator.check(responseContent)).willReturn(false);

        // when
        final SummarizationResult result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
        assertThat(result.exception()).isInstanceOf(SummarizationResponseInvalidException.class);
        assertThat(result.summary()).isNull();
    }

}