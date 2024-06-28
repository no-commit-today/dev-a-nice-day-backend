package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
public class OpenAiCollectionProcessor implements CollectionProcessor {

    private static final Pattern CATEGORIZATION_RESULT_PATTERN = Pattern.compile("^-\\s("
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    private static final Pattern SUMMARIZATION_RESULT_PATTERN = Pattern.compile("^-\\s.+$");

    private final OpenAiService openAiService;

    @Override
    public CategorizationResult categorize(final Prompt prompt, final CollectedContent content) {
        final ChatCompletionRequest request = createRequest(prompt, content);
        final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
        log.debug("ChatCompletionResponse: {}", chatCompletionResponse);

        final List<String> result = Arrays.stream(
                        chatCompletionResponse.getChoices().get(0).getMessage().getContent().split("\n"))
                .filter(message -> !message.isBlank())
                .map(String::trim)
                .toList();

        final boolean resultIncorrect = result.isEmpty() || result.stream()
                .anyMatch(line -> !CATEGORIZATION_RESULT_PATTERN.matcher(line).matches());

        if (resultIncorrect) {
            return CategorizationResult.failure();
        }

        return CategorizationResult.success(
                result.stream()
                        .map(line -> line.replaceFirst("^-\\s", ""))
                        .map(CollectionCategory::valueOf)
                        .toList()
        );
    }

    @Override
    public SummarizationResult summarize(final Prompt prompt, final CollectedContent content) {
        final ChatCompletionRequest request = createRequest(prompt, content);
        final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
        log.debug("ChatCompletionResponse: {}", chatCompletionResponse);

        final String summary = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        final boolean resultCorrect = validateSummary(summary);

        if (!resultCorrect) {
            return SummarizationResult.failure();
        }

        return SummarizationResult.success(summary);
    }

    private static ChatCompletionRequest createRequest(final Prompt prompt, final CollectedContent content) {
        return ChatCompletionRequest.builder()
                .model(prompt.getModel())
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt.getContent()),
                        new ChatMessage(ChatMessageRole.USER.value(),
                                content.getTitle() + "\n\n" + content.getContent())
                )).build();
    }

    private boolean validateSummary(final String summary) {
        final String[] summarySplit = summary.split("\n");
        if (summarySplit.length == 0 || summarySplit.length > 5) {
            return false;
        }
        return Arrays.stream(summarySplit)
                .allMatch(line -> SUMMARIZATION_RESULT_PATTERN.matcher(line).matches());
    }
}
