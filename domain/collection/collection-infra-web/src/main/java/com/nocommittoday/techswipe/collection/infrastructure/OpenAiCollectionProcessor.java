package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
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

    private static final String CATEGORIZATION_PROMPT = String.format("""
            - 당신은 분류를 정말 잘 하는 봇입니다.
            - 카테고리는 [%s] 중 선택합니다.
            - 카테고리는 최소 1개에서 최대 3개입니다.
            - 아래 [답변 형식]에 맞게 답변해야 합니다.
            - 지시한 내용들을 지키지 못하면 당신은 불이익을 받을 것입니다.
            
            [답변 형식]
            - ...
            - ...
            """,
            Arrays.stream(CollectionCategory.values())
                    .map(CollectionCategory::name)
                    .collect(Collectors.joining(","))
    );

    private static final Pattern SUMMARIZATION_RESULT_PATTERN = Pattern.compile("^-\\s.+$");

    private static final String SUMMARIZATION_PROMPT = """
            - 당신은 요약을 정말 잘 하는 봇입니다.
            - 요약은 최대 5줄입니다.
            - 아래 [답변 형식]에 맞게 답변해야 합니다.
            - 지시한 내용들을 지키지 못하면 당신은 불이익을 받을 것입니다.
            
            [답변 형식]
            - ...
            - ...
            - ...
            ...
            """;

    private final OpenAiService openAiService;
    private final String categorizationModel;

    @Override
    public CategorizationResult categorize(final CollectedContent content) {
        final ChatCompletionRequest request = createRequest(categorizationModel, CATEGORIZATION_PROMPT, content);
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
    public SummarizationResult summarize(final CollectedContent content) {

        final ChatCompletionRequest request = createRequest("gpt-3.5-turbo-0125", SUMMARIZATION_PROMPT, content);
        final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
        log.debug("ChatCompletionResponse: {}", chatCompletionResponse);

        final String summary = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        final boolean resultCorrect = validateSummary(summary);

        if (!resultCorrect) {
            return SummarizationResult.failure();
        }

        return SummarizationResult.success(summary);
    }

    private static ChatCompletionRequest createRequest(
            final String model, final String prompt, final CollectedContent content) {
        return ChatCompletionRequest.builder()
                .model(model)
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt),
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
