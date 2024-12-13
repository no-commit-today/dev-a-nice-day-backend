package com.devniceday.batch.domain;

import com.devniceday.batch.domain.openai.CategorizationClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContentCategorizer {

    private final CategorizationClient categorizationClient;

    public ContentCategorizer(CategorizationClient categorizationClient) {
        this.categorizationClient = categorizationClient;
    }

    @Retryable(retryFor = CategorizationException.class)
    public List<CollectionCategory> categorize(CategorizationPrompt prompt) {
        String responseContent = categorizationClient.categorize(prompt);
        List<CollectionCategory> categoryList = CollectionCategory.createListFromChatCompletionResult(responseContent);
        if (categoryList == null) {
            throw new CategorizationException("카테고리 분류에 실패했습니다.\n"
                    + "[prompt]\n" + prompt + "\n"
                    + "[responseContent]\n" + responseContent);
        }
        return categoryList;
    }
}
