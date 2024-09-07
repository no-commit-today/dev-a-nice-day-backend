package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
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
class SummarizationProcessorTest {

    @InjectMocks
    private SummarizationProcessor summarizationProcessor;

    @Mock
    private SummarizationClient summarizationClient;

    @Test
    void 내용을_형식에_맞게_반환한다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        String responseContent = """
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim();
        given(summarizationClient.summarize(collectedContent)).willReturn(responseContent);


        // when
        var result = summarizationProcessor.summarize(collectedContent);

        // then
        assertThat(result.getContent()).isEqualTo("""
                1. 요약 1
                2. 요약 2
                3. 요약 3
                """.trim());
    }

    @Test
    void 내용이_형식에_맞지_않을_경우_예외를_발생시킨다() {
        // given
        CollectedContent collectedContent = mock(CollectedContent.class);
        String responseContent = """
                1. 요약 1
                2. 요약 2
                """.trim();
        given(summarizationClient.summarize(collectedContent)).willReturn(responseContent);

        // when
        // then
        assertThatThrownBy(() -> summarizationProcessor.summarize(collectedContent))
                .isInstanceOf(DomainValidationException.class);
    }

}