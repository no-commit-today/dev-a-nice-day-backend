package com.nocommittoday.client.openai.model;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <a href="https://platform.openai.com/docs/api-reference/chat/create">Create chat completion</a>
 */
public record ChatCompletionCreate(
        ChatModel model,
        List<MessageRequest> messages,
        @Nullable Double temperature
) {

    public ChatCompletionCreate {
        if (model == null) {
            throw new IllegalArgumentException("model is required");
        }
        if (CollectionUtils.isEmpty(messages)) {
            throw new IllegalArgumentException("messages is required");
        }
    }

    public static ChatCompletionRequestBuilder builder() {
        return new ChatCompletionRequestBuilder();
    }

    public static final class ChatCompletionRequestBuilder {
        private ChatModel model;
        private List<MessageRequest> messages;
        private Double temperature;

        private ChatCompletionRequestBuilder() {
        }

        public static ChatCompletionRequestBuilder aChatCompletionRequest() {
            return new ChatCompletionRequestBuilder();
        }

        public ChatCompletionRequestBuilder model(final ChatModel model) {
            this.model = model;
            return this;
        }

        public ChatCompletionRequestBuilder messages(final List<MessageRequest> messages) {
            if (messages == null) {
                this.messages = null;
                return this;
            }
            this.messages = new ArrayList<>(messages);
            return this;
        }

        public ChatCompletionRequestBuilder temperature(final Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public ChatCompletionCreate build() {
            return new ChatCompletionCreate(model, messages, temperature);
        }
    }
}
