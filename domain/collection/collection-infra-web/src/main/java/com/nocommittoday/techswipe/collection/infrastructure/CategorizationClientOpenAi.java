package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;

@Slf4j
class CategorizationClientOpenAi implements CategorizationClient {

    private final ChatClient chatClient;

    public CategorizationClientOpenAi(
            final ChatClient.Builder chatClientBuilder, String model
    ) {
        log.info("분류에 사용되는 모델: {}", model);
        this.chatClient = chatClientBuilder
                .defaultOptions(OpenAiChatOptions.builder()
                        .withModel(model)
                        .build())
                .build();
    }

    @Override
    public String categorize(final CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new CategorizationLoggerAdvisor(collectedContent))
                .system(CategorizationConst.PROMPT)
                .user(collectedContent.getTitle() + "\n\n" + collectedContent.getContent())
                .call()
                .content()
                .trim();
    }
}
