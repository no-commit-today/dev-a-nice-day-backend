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
public class CollectionProcessorOpenAi implements CollectionProcessor {

    private static final Pattern CATEGORIZATION_RESULT_PATTERN = Pattern.compile("^-\\s("
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    public static final String CATEGORIZATION_PROMPT = String.format("""
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

    public static final String SUMMARIZATION_PROMPT = """
            - 당신은 요약을 정말 잘 하는 봇입니다.
            - 요약은 최소 1줄에서 최대 5줄입니다.
            - 아래 [답변 형식]에 맞게 답변해야 합니다.
            - 지시한 내용들을 지키지 못하면 당신은 불이익을 받을 것입니다.
            
            [답변 형식]
            - ...
            - ...
            - ...
            - ...
            - ...
            """;

    private final OpenAiService openAiService;
    private final String categorizationModel;

    @Override
    public CategorizationResult categorize(final CollectedContent collectedContent) {
        try {
            final ChatCompletionRequest request = createRequest(
                    categorizationModel, CATEGORIZATION_PROMPT, collectedContent);
            final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
            log.debug("categorization ChatCompletionResponse: {}", chatCompletionResponse);

            final String responseContent = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
            final List<String> responseContentLines = Arrays.stream(
                            responseContent.split("\n"))
                    .filter(message -> !message.isBlank())
                    .map(String::trim)
                    .toList();


            if (responseContentLines.stream()
                    .anyMatch(line -> !CATEGORIZATION_RESULT_PATTERN.matcher(line).matches())) {
                throw new CategorizationResponseInvalidException(
                        "분류 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent);
            }

            final List<CollectionCategory> categories = responseContentLines.stream()
                    .map(line -> line.replaceFirst("^-\\s", ""))
                    .map(CollectionCategory::valueOf)
                    .distinct()
                    .toList();

            log.warn("open ai 카테고리 분류 요청이 중복된 응답을 발생시켰습니다. contentId={}, responseContent={}, categories={}",
                    collectedContent.getId(), responseContent, categories
            );

            if (categories.size() < CollectionProcessor.MIN_CATEGORY_NUM
                    || categories.size() > CollectionProcessor.MAX_CATEGORY_NUM) {
                throw new CategorizationResponseInvalidException(
                        "분류 결과는 " + CollectionProcessor.MIN_CATEGORY_NUM + "개에서 "
                                + CollectionProcessor.MAX_CATEGORY_NUM + "개 사이여야 합니다. ",
                        collectedContent.getId(), responseContent);
            }

            return CategorizationResult.success(
                    categories
            );
        } catch (final Exception ex) {
            return CategorizationResult.failure(ex);
        }
    }


    @Override
    public SummarizationResult summarize(final CollectedContent content) {
        try {
            final ChatCompletionRequest request = createRequest(
                    "gpt-3.5-turbo-0125", SUMMARIZATION_PROMPT, content);
            final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
            log.debug("summarization ChatCompletionResponse: {}", chatCompletionResponse);

            final String responseContent = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
            final List<String> responseContentLines = Arrays.stream(
                            responseContent.split("\n"))
                    .filter(message -> !message.isBlank())
                    .map(String::trim)
                    .toList();
            if (responseContentLines.stream()
                    .anyMatch(line -> !SUMMARIZATION_RESULT_PATTERN.matcher(line).matches())) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과가 올바르지 않습니다. ", content.getId(), responseContent);
            }

            if (responseContentLines.size() < CollectionProcessor.MIN_SUMMARY_LINE ||
                    responseContentLines.size() > CollectionProcessor.MAX_SUMMARY_LINE) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과는 " + CollectionProcessor.MIN_SUMMARY_LINE + "줄에서 "
                                + CollectionProcessor.MAX_SUMMARY_LINE + "줄 사이여야 합니다. ",
                        content.getId(), responseContent);
            }
            return SummarizationResult.success(responseContent);

        } catch (final Exception ex) {
            return SummarizationResult.failure(ex);
        }
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
