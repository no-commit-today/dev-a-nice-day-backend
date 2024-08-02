package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.service.CollectionSummaryRegisterCommand;
import com.nocommittoday.techswipe.collection.service.CollectionSummaryRegisterService;
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
    public ResponseEntity<String> getPrompt(@PathVariable Long collectionId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(summaryRegisterService.getPrompt(new CollectedContentId(collectionId)).content());
    }

    @PostMapping("/admin/api/collections/{collectionId}/summary")
    public void register(
            @PathVariable Long collectionId,
            @RequestBody @Validated CollectionSummaryRegisterRequest registerRequest
    ) {
        summaryRegisterService.register(new CollectionSummaryRegisterCommand(
                new CollectedContentId(collectionId), registerRequest.summary())
        );
    }
}
