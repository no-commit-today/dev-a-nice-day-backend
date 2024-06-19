package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptRegister;
import com.nocommittoday.techswipe.collection.infrastructure.PromptAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptRegisterService {

    private final PromptAppender promptAppender;

    public Prompt.PromptId register(final PromptRegister promptRegister) {
        return promptAppender.save(promptRegister);
    }
}
