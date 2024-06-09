package com.nocommittoday.techswipe.batch.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;

public class CategorizeFailureException extends RuntimeException {

    public CategorizeFailureException(
            final CollectedContent.CollectedContentId collectedContentId, final Prompt.PromptId promptId
    ) {
        super("카테고리 분류에 실패했습니다. collectedContentId=" + collectedContentId + ", promptId=" + promptId);
    }
}