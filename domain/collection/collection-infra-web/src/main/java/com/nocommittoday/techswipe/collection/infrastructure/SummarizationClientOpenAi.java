package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import org.springframework.ai.chat.client.ChatClient;

class SummarizationClientOpenAi implements SummarizationClient {

    private final ChatClient chatClient;

    public SummarizationClientOpenAi(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String summarize(final CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new SummarizationLoggerAdvisor(collectedContent))
                .user(SummarizationPromptUtils.create(collectedContent))
                .call()
                .content()
                .trim();
    }
}
