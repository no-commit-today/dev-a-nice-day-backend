package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.core.controller.servlet.ListResponse;
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
    public ListResponse<ImageSyncQueryResponse> getList(
            @Validated @ModelAttribute final ImageSyncQueryRequest request) {
        return new ListResponse<>(
                imageSyncQueryService.getList(new ImageSyncQueryParam(request.from(), request.to()))
                        .stream()
                        .map(ImageSyncQueryResponse::from)
                        .toList()
        );
    }
}
