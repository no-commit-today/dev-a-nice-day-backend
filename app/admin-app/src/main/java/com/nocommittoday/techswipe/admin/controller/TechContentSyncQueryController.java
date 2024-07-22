package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderSync;
import com.nocommittoday.techswipe.content.service.TechContentSyncQueryParam;
import com.nocommittoday.techswipe.content.service.TechContentSyncQueryService;
import com.nocommittoday.techswipe.core.controller.servlet.ListResponse;
import com.nocommittoday.techswipe.core.controller.servlet.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechContentSyncQueryController {

    private final TechContentSyncQueryService techContentSyncQueryService;

    @GetMapping("/api/content/admin/sync-contents")
    public ListResponse<TechContentSyncQueryResponse> getList(
            @Validated @ModelAttribute final TechContentSyncQueryRequest request,
            @Validated @ModelAttribute final PageRequest pageRequest
    ) {
        return new ListResponse<>(
                techContentSyncQueryService.getList(
                                pageRequest.toPageParam(),
                                new TechContentSyncQueryParam(request.from(), request.to())
                        )
                        .stream()
                        .map(TechContentSyncQueryResponse::from)
                        .toList()
        );
    }

    @GetMapping("/api/content/admin/sync-providers")
    public ListResponse<TechContentProviderSync> getProviderList(
            @Validated @ModelAttribute final TechContentSyncQueryRequest request,
            @Validated @ModelAttribute final PageRequest pageRequest
    ) {
        return new ListResponse<>(
                techContentSyncQueryService.getProviderList(
                        pageRequest.toPageParam(),
                        new TechContentSyncQueryParam(request.from(), request.to())
                )
        );
    }
}
