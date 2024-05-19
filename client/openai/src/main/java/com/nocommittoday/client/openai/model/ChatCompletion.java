package com.nocommittoday.client.openai.model;

import java.util.List;

/**
 * <a href="https://platform.openai.com/docs/api-reference/chat/object">The chat completion object</a>
 */
public record ChatCompletion(
        String id,
        ChatObject object,
        int created,
        ChatModel model,
        String systemFingerprint,
        List<Choice> choices,
        Usage usage
) {

}
