package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.SummarizationPrompt;
import org.springframework.ai.chat.client.ChatClient;

class SummarizationClientOpenAi implements SummarizationClient {

    private final ChatClient chatClient;

    public SummarizationClientOpenAi(
            ChatClient.Builder chatClientBuilder
    ) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String summarize(CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new SummarizationLoggerAdvisor(collectedContent))
                .user(SummarizationPrompt.of(collectedContent).getContent())
                .call()
                .content()
                .trim();
    }
}
