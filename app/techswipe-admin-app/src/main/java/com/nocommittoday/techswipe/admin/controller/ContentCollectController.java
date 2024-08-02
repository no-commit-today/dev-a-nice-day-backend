package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.service.ContentCollectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentCollectController {

    private final ContentCollectService contentCollectService;

    public ContentCollectController(ContentCollectService contentCollectService) {
        this.contentCollectService = contentCollectService;
    }

    @PostMapping("/api/collection/admin/collections")
    public ContentCollectResponse collect(
            @RequestBody @Validated ContentCollectRequest request
    ) {
        return ContentCollectResponse.from(
                contentCollectService.collect(request.toCommand())
        );
    }
}
