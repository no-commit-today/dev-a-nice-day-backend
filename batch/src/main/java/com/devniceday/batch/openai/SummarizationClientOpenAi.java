package com.devniceday.batch.openai;

import com.devniceday.batch.domain.SummarizationPrompt;
import org.springframework.ai.chat.client.ChatClient;

class SummarizationClientOpenAi implements SummarizationClient {

    private final ChatClient chatClient;

    public SummarizationClientOpenAi(
            ChatClient.Builder chatClientBuilder
    ) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String summarize(SummarizationPrompt prompt) {
        return chatClient.prompt()
                .user(prompt.getUser())
                .call()
                .content()
                .trim();
    }
}
