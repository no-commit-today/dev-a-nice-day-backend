package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.service.CollectionSummaryRegisterCommand;
import com.nocommittoday.techswipe.collection.service.CollectionSummaryRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollectionSummaryRegisterController {

    private final CollectionSummaryRegisterService summaryRegisterService;

    @GetMapping("/api/collection/admin/collections/{collectionId}/summarization-prompt")
    public ResponseEntity<String> getPrompt(
            @PathVariable final Long collectionId
    ) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(summaryRegisterService.getPrompt(new CollectedContent.Id(collectionId)).content());
    }

    @PostMapping("/api/collection/admin/collections/{collectionId}/summary")
    public void register(
            @PathVariable Long collectionId,
            @RequestBody @Validated CollectionSummaryRegisterRequest registerRequest
    ) {
        summaryRegisterService.register(new CollectionSummaryRegisterCommand(
                new CollectedContent.Id(collectionId), registerRequest.summary())
        );
    }
}
