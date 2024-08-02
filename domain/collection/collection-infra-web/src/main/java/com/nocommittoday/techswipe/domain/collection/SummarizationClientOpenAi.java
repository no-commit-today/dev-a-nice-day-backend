package com.nocommittoday.techswipe.domain.collection;

import org.springframework.ai.chat.client.ChatClient;

class SummarizationClientOpenAi implements SummarizationClient {

    private final ChatClient chatClient;

    private final SummarizationPromptCreator promptCreator;

    public SummarizationClientOpenAi(
            final ChatClient.Builder chatClientBuilder,
            final SummarizationPromptCreator promptCreator
    ) {
        this.chatClient = chatClientBuilder.build();
        this.promptCreator = promptCreator;
    }

    @Override
    public String summarize(CollectedContent collectedContent) {
        return chatClient.prompt()
                .advisors(new SummarizationLoggerAdvisor(collectedContent))
                .user(promptCreator.create(collectedContent))
                .call()
                .content()
                .trim();
    }
}
