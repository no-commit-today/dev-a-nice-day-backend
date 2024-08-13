package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.CollectionSummaryRegisterRequest;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionSummaryRegisterCommand;
import com.nocommittoday.techswipe.domain.collection.CollectionSummaryRegisterService;
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
public class CollectionSummaryRegisterController {

    private final CollectionSummaryRegisterService summaryRegisterService;

    public CollectionSummaryRegisterController(CollectionSummaryRegisterService summaryRegisterService) {
        this.summaryRegisterService = summaryRegisterService;
    }

    @GetMapping("/admin/api/collections/{collectionId}/summarization-prompt")
    public ResponseEntity<String> getPrompt(AdminApiUser adminApiUser, @PathVariable Long collectionId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(summaryRegisterService.getPrompt(new CollectedContentId(collectionId)).content());
    }

    @PostMapping("/admin/api/collections/{collectionId}/summary")
    public void register(
            AdminApiUser adminApiUser,
            @PathVariable Long collectionId,
            @RequestBody @Validated CollectionSummaryRegisterRequest registerRequest
    ) {
        summaryRegisterService.register(new CollectionSummaryRegisterCommand(
                new CollectedContentId(collectionId), registerRequest.summary())
        );
    }
}
