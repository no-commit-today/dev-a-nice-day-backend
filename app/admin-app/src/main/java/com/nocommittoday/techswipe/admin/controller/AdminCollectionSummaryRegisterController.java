package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.AdminCollectionSummaryRegisterRequest;
import com.nocommittoday.techswipe.admin.domain.AdminCollectionSummaryRegisterService;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminCollectionSummaryRegisterController {

    private final AdminCollectionSummaryRegisterService summaryRegisterService;

    public AdminCollectionSummaryRegisterController(AdminCollectionSummaryRegisterService summaryRegisterService) {
        this.summaryRegisterService = summaryRegisterService;
    }

    @GetMapping("/admin/api/collections/{collectionId}/summarization-prompt")
    public ResponseEntity<String> getPrompt(AdminApiUser adminApiUser, @PathVariable Long collectionId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(summaryRegisterService.getPrompt(new CollectedContentId(collectionId)).getContent());
    }

    @PostMapping("/admin/api/collections/{collectionId}/summary")
    public void register(
            AdminApiUser adminApiUser,
            @PathVariable Long collectionId,
            @RequestBody @Validated AdminCollectionSummaryRegisterRequest registerRequest
    ) {
        summaryRegisterService.register(
                new CollectedContentId(collectionId), new Summary(registerRequest.summary())
        );
    }
}
