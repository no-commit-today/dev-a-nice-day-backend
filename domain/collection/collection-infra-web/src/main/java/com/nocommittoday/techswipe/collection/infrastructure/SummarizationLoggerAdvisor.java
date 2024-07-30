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
class SummarizationLoggerAdvisor implements RequestResponseAdvisor {

    private final CollectedContent collectedContent;

    private long startTime;

    @Override
    public AdvisedRequest adviseRequest(AdvisedRequest request, Map<String, Object> context) {
        OpenAiChatOptions openAiChatOptions = (OpenAiChatOptions) request.chatOptions();
        log.trace("요약 요청 전체 내용[{}]: {}", collectedContent.getId(), request);
        log.info("요약 요청 id={}, model={}", collectedContent.getId(), openAiChatOptions.getModel());
        startTime = System.currentTimeMillis();
        return request;
    }

    @Override
    public Flux<ChatResponse> adviseResponse(Flux<ChatResponse> fluxResponse, Map<String, Object> context) {
        return fluxResponse.map(response -> {
            responseLog(response);
            return response;
        });
    }

    @Override
    public ChatResponse adviseResponse(ChatResponse response, Map<String, Object> context) {
        responseLog(response);
        return response;
    }

    private void responseLog(ChatResponse response) {
        log.trace("요약 응답 전체 내용[{}]: {}", collectedContent.getId(), response);
        log.info("요약 응답 [{}]: promptToken={}, generationToken={}, totalToken={}, 소요시간={}s",
                collectedContent.getId(),
                response.getMetadata().getUsage().getPromptTokens(),
                response.getMetadata().getUsage().getGenerationTokens(),
                response.getMetadata().getUsage().getTotalTokens(),
                (System.currentTimeMillis() - startTime) / 1000.0
        );
    }
}
