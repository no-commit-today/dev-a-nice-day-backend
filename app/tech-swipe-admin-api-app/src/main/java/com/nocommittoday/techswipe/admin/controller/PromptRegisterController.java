package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.service.PromptRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromptRegisterController {

    private final PromptRegisterService promptRegisterService;

    @PostMapping("/api/collection/admin/prompts")
    public Prompt.PromptId register(
            @RequestBody @Validated final PromptRegisterRequest request
    ) {
        return promptRegisterService.register(request.toCommand());
    }
}
