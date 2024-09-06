package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.content.v1.request.TechContentListQueryRequest;
import com.nocommittoday.techswipe.controller.content.v1.response.TechContentCountResponse;
import com.nocommittoday.techswipe.controller.content.v1.response.TechContentQueryResponse;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryParam;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryService;
import com.nocommittoday.techswipe.controller.core.ListResponse;
import com.nocommittoday.techswipe.controller.core.PageRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechContentListQueryController {

    private final TechContentListQueryService techContentListQueryService;

    public TechContentListQueryController(TechContentListQueryService techContentListQueryService) {
        this.techContentListQueryService = techContentListQueryService;
    }

    @GetMapping("/api/content/v1/contents")
    public ResponseEntity<ListResponse<TechContentQueryResponse>> getList(
            @ModelAttribute @Valid PageRequest pageRequest,
            @ModelAttribute TechContentListQueryRequest request
    ) {

        TechContentListQueryResult result = techContentListQueryService.getList(
                pageRequest.toPageParam(), new TechContentListQueryParam(request.categories())
        );

        return ResponseEntity.ok(new ListResponse<>(
                result.content().stream()
                        .map(TechContentQueryResponse::from)
                        .toList())
        );
    }

    @GetMapping("/api/content/v1/contents-count")
    public ResponseEntity<TechContentCountResponse> count(
            @ModelAttribute TechContentListQueryRequest request
    ) {
        return ResponseEntity.ok(new TechContentCountResponse(
                techContentListQueryService.count(new TechContentListQueryParam(request.categories()))
        ));
    }

}
