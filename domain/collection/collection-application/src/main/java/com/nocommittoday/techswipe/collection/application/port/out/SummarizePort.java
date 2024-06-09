package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.Prompt;

public interface SummarizePort {
    SummarizationResult summarize(Prompt prompt, String content);
}
