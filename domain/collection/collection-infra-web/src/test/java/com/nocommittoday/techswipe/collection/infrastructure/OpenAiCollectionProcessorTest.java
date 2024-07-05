package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OpenAiCollectionProcessorTest {

    @InjectMocks
    private OpenAiCollectionProcessor openAiCollectionProcessor;

    @Mock
    private OpenAiService openAiService;

    @Test
    void openai의_카테고리_분류_결과를_리스트로_반환한다() {
        // given
        final ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        final ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        final ChatMessage chatMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(),
                "- " + CollectionCategory.SERVER.name() + "\n" +
                        "- " + CollectionCategory.SW_ENGINEERING.name()
        );
        chatCompletionChoice.setMessage(chatMessage);
        chatCompletionResult.setChoices(List.of(chatCompletionChoice));
        given(openAiService.createChatCompletion(any()))
                .willReturn(chatCompletionResult);

        // when
        final CollectedContent collectedContent = new CollectedContent(
                null,
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "collected content",
                "image-url"
        );
        final CategorizationResult result = openAiCollectionProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.categories()).containsExactlyInAnyOrder(
                CollectionCategory.SERVER,
                CollectionCategory.SW_ENGINEERING
        );
    }

    @Test
    void openai의_카테고리_분류_결과가_형식에_맞지않으면_실패를_반환한다() {
        // given
        final ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        final ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        final ChatMessage chatMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(),
                "invalid"
        );
        chatCompletionChoice.setMessage(chatMessage);
        chatCompletionResult.setChoices(List.of(chatCompletionChoice));
        given(openAiService.createChatCompletion(any()))
                .willReturn(chatCompletionResult);

        // when
        final CollectedContent collectedContent = new CollectedContent(
                null,
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "collected content",
                "image-url"
        );
        final CategorizationResult result = openAiCollectionProcessor.categorize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
    }

    @Test
    void openai의_요약_결과를_반환한다() {
        // given
        final ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        final ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        final ChatMessage chatMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(),
                """
                        - 요약1
                        - 요약2
                        - 요약3
                        """
        );
        chatCompletionChoice.setMessage(chatMessage);
        chatCompletionResult.setChoices(List.of(chatCompletionChoice));
        given(openAiService.createChatCompletion(any()))
                .willReturn(chatCompletionResult);

        // when
        final CollectedContent collectedContent = new CollectedContent(
                null,
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "collected content",
                "image-url"
        );
        final SummarizationResult result = openAiCollectionProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isTrue();
        assertThat(result.summary()).isEqualTo("""
                - 요약1
                - 요약2
                - 요약3
                """);
    }

    @Test
    void openai의_요약_결과가_형식에_맞지_않으면_실패_결과를_반환한다() {
        // given
        final ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        final ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        final ChatMessage chatMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(),
                """
                        invalid
                        """
        );
        chatCompletionChoice.setMessage(chatMessage);
        chatCompletionResult.setChoices(List.of(chatCompletionChoice));
        given(openAiService.createChatCompletion(any()))
                .willReturn(chatCompletionResult);

        // when
        final CollectedContent collectedContent = new CollectedContent(
                null,
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "collected content",
                "image-url"
        );
        final SummarizationResult result = openAiCollectionProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
    }

    @Test
    void openai의_요약_결과가_5줄보다_많으면_실패_결과를_반환한다() {
        // given

        final ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        final ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        final ChatMessage chatMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(),
                """
                        - 요약1
                        - 요약2
                        - 요약3
                        - 요약4
                        - 요약5
                        - 요약6
                        """
        );
        chatCompletionChoice.setMessage(chatMessage);
        chatCompletionResult.setChoices(List.of(chatCompletionChoice));
        given(openAiService.createChatCompletion(any()))
                .willReturn(chatCompletionResult);

        // when
        final CollectedContent collectedContent = new CollectedContent(
                null,
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "collected content",
                "image-url"
        );
        final SummarizationResult result = openAiCollectionProcessor.summarize(collectedContent);

        // then
        assertThat(result.success()).isFalse();
    }
}