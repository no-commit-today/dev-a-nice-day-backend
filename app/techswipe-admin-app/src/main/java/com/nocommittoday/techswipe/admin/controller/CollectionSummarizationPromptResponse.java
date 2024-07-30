package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.service.CollectionSummarizationPromptResult;

public record CollectionSummarizationPromptResponse(
        String content
) {

    public static CollectionSummarizationPromptResponse from(CollectionSummarizationPromptResult result) {
        return new CollectionSummarizationPromptResponse(result.content());
    }
}
