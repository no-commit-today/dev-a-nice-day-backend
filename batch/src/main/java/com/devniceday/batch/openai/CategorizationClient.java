package com.devniceday.batch.openai;

import com.devniceday.batch.domain.CategorizationPrompt;

public interface CategorizationClient {

    String categorize(CategorizationPrompt prompt);
}
