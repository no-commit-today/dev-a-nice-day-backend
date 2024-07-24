package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.service.ContentCollectManuallyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentCollectController {

    private final ContentCollectManuallyService contentCollectManuallyService;

    @PostMapping("/api/collection/admin/collections")
    public ContentCollectResponse collect(
            @RequestBody @Validated final ContentCollectRequest request
    ) {
        return ContentCollectResponse.from(
                contentCollectManuallyService.collect(request.toCommand())
        );
    }
}
