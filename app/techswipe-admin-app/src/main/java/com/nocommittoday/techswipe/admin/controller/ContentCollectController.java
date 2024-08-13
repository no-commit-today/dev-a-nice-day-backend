package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.ContentCollectRequest;
import com.nocommittoday.techswipe.admin.controller.response.ContentCollectResponse;
import com.nocommittoday.techswipe.domain.collection.ContentCollectService;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
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

    @PostMapping("/admin/api/collections")
    public ContentCollectResponse collect(
            AdminApiUser adminApiUser,
            @RequestBody @Validated ContentCollectRequest request
    ) {
        return ContentCollectResponse.from(
                contentCollectService.collect(request.toCommand())
        );
    }
}
