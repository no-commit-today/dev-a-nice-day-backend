package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;

class CategorizationClientOpenAi implements CategorizationClient {

    private static final Logger log = LoggerFactory.getLogger(CategorizationClientOpenAi.class);

    private final ChatClient chatClient;

    public CategorizationClientOpenAi(
            ChatClient.Builder chatClientBuilder, String model
    ) {
        log.info("분류에 사용되는 모델: {}", model);
        this.chatClient = chatClientBuilder
                .defaultOptions(OpenAiChatOptions.builder()
                        .withModel(model)
                        .build())
                .build();
    }

    @Override
    public String categorize(CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new CategorizationLoggerAdvisor(collectedContent))
                .system(CategorizationConst.PROMPT)
                .user(collectedContent.getTitle() + "\n\n" + collectedContent.getContent())
                .call()
                .content()
                .trim();
    }
}
