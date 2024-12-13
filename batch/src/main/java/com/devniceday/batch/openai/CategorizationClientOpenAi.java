package com.devniceday.batch.openai;

import com.devniceday.batch.domain.CategorizationPrompt;
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
    public String categorize(CategorizationPrompt prompt) {
        return chatClient.prompt()
                .system(prompt.getSystem())
                .user(prompt.getUser())
                .call()
                .content()
                .trim();
    }
}
