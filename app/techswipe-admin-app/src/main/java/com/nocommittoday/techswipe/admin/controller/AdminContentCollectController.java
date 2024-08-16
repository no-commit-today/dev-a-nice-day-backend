package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.AdminContentCollectRequest;
import com.nocommittoday.techswipe.admin.domain.AdminContentCollectService;
import com.nocommittoday.techswipe.controller.core.IdResponse;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminContentCollectController {

    private final AdminContentCollectService contentCollectService;

    public AdminContentCollectController(AdminContentCollectService contentCollectService) {
        this.contentCollectService = contentCollectService;
    }

    @PostMapping("/admin/api/collections")
    public IdResponse<Long> collect(
            AdminApiUser adminApiUser,
            @RequestBody @Validated AdminContentCollectRequest request
    ) {
        return new IdResponse<>(
                contentCollectService.collect(request.toCommand()).value()
        );
    }
}
