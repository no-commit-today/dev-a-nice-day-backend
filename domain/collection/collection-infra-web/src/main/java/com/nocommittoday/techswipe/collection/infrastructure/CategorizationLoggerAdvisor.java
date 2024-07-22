package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.AdvisedRequest;
import org.springframework.ai.chat.client.RequestResponseAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import reactor.core.publisher.Flux;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
class CategorizationLoggerAdvisor implements RequestResponseAdvisor {

    private final CollectedContent collectedContent;

    private long startTime;

    @Override
    public AdvisedRequest adviseRequest(final AdvisedRequest request, final Map<String, Object> context) {
        final OpenAiChatOptions openAiChatOptions = (OpenAiChatOptions) request.chatOptions();
        log.trace("분류 요청 전체 내용[{}]: {}", collectedContent.getId(), request);
        log.info("분류 요청 id={}, model={}", collectedContent.getId(), openAiChatOptions.getModel());
        startTime = System.currentTimeMillis();
        return request;
    }

    @Override
    public Flux<ChatResponse> adviseResponse(final Flux<ChatResponse> fluxResponse, final Map<String, Object> context) {
        return fluxResponse.map(response -> {
            responseLog(response);
            return response;
        });
    }

    @Override
    public ChatResponse adviseResponse(final ChatResponse response, final Map<String, Object> context) {
        responseLog(response);
        return response;
    }

    private void responseLog(final ChatResponse response) {
        log.trace("분류 응답 전체 내용[{}]: {}", collectedContent.getId(), response);
        log.info("분류 응답 [{}]: promptToken={}, generationToken={}, totalToken={}, 소요시간={}s",
                collectedContent.getId(),
                response.getMetadata().getUsage().getPromptTokens(),
                response.getMetadata().getUsage().getGenerationTokens(),
                response.getMetadata().getUsage().getTotalTokens(),
                (System.currentTimeMillis() - startTime) / 1000.0
        );
    }
}
