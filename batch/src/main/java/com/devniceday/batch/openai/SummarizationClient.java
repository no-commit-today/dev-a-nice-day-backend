package com.devniceday.batch.openai;

import com.devniceday.batch.domain.SummarizationPrompt;

public interface SummarizationClient {

    String summarize(SummarizationPrompt prompt);
}
