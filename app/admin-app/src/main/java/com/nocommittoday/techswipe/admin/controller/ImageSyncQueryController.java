package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.core.controller.servlet.ListResponse;
import com.nocommittoday.techswipe.core.controller.servlet.PageRequest;
import com.nocommittoday.techswipe.image.domain.ImageSync;
import com.nocommittoday.techswipe.image.service.ImageSyncQueryParam;
import com.nocommittoday.techswipe.image.service.ImageSyncQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageSyncQueryController {

    private final ImageSyncQueryService imageSyncQueryService;

    @GetMapping("/api/image/admin/sync-images")
    public ListResponse<ImageSync> getList(
            @Validated @ModelAttribute final ImageSyncQueryRequest request,
            @Validated @ModelAttribute final PageRequest pageRequest
    ) {
        return new ListResponse<>(
                imageSyncQueryService.getList(
                        pageRequest.toPageParam(),
                        new ImageSyncQueryParam(request.from(), request.to())
                )
        );
    }
}
