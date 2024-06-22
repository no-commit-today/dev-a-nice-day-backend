package com.nocommittoday.techswipe.batch.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;

public class SummarizeFailureException extends RuntimeException {

    public SummarizeFailureException(
            final CollectedContent.Id collectedContentId, final Prompt.PromptId promptId
    ) {
        super("내용 요약에 실패했습니다. collectedContentId=" + collectedContentId + ", promptId=" + promptId);
    }
}
