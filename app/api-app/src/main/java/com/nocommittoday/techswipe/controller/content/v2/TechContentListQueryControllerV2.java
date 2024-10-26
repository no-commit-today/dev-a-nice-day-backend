package com.nocommittoday.techswipe.controller.content.v2;

import com.nocommittoday.techswipe.controller.content.v1.response.TechContentQueryResponse;
import com.nocommittoday.techswipe.controller.content.v2.request.TechContentListQueryRequest;
import com.nocommittoday.techswipe.controller.core.ListResponse;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryServiceNew;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechContentListQueryControllerV2 {

    private final TechContentListQueryServiceNew techContentListQueryService;

    public TechContentListQueryControllerV2(TechContentListQueryServiceNew techContentListQueryService) {
        this.techContentListQueryService = techContentListQueryService;
    }

    @GetMapping("/api/content/v2/contents")
    public ResponseEntity<ListResponse<TechContentQueryResponse>> getList(
            ApiUserOrGuest apiUserOrGuest,
            @ModelAttribute @Validated TechContentListQueryRequest request
    ) {
        TechContentListQueryResult result = techContentListQueryService.getList(
                apiUserOrGuest, request.toParam());

        return ResponseEntity.ok(new ListResponse<>(
                result.content().stream()
                        .map(TechContentQueryResponse::from)
                        .toList())
        );
    }
}
