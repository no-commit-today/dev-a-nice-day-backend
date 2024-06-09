package com.nocommittoday.techswipe.collection.adapter.out.web;

import com.nocommittoday.techswipe.collection.application.port.out.CategorizePort;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class CategorizeAdapter implements CategorizePort {

    private static final String RESULT_PATTERN = "^-\\s?("
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$";

    private final OpenAiService openAiService;

    @Override
    public List<CollectionCategory> categorize(final Prompt prompt, final String content) {
        final ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(prompt.getModel())
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt.getContent()),
                        new ChatMessage(ChatMessageRole.USER.value(), content)
                )).build();
        final ChatCompletionResult chatCompletionResponse = openAiService.createChatCompletion(request);
        log.debug("ChatCompletionResponse: {}", chatCompletionResponse);

        final List<String> result = Arrays.stream(
                        chatCompletionResponse.getChoices().get(0).getMessage().getContent().split("\n"))
                .filter(String::isBlank)
                .map(String::trim)
                .toList();

        final boolean resultIncorrect = result.isEmpty() || result.stream()
                .anyMatch(line -> !line.matches(RESULT_PATTERN));

        if (resultIncorrect) {
            return List.of();
        }

        return result.stream()
                .map(line -> line.replaceFirst("^-\\s?", ""))
                .map(CollectionCategory::valueOf)
                .toList();
    }
}
