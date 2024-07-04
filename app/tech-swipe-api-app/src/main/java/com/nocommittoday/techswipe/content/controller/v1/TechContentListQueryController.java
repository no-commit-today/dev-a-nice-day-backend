package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.service.TechContentListQueryParam;
import com.nocommittoday.techswipe.content.service.TechContentListQueryService;
import com.nocommittoday.techswipe.content.service.TechContentQueryResult;
import com.nocommittoday.techswipe.core.controller.servlet.ListResponse;
import com.nocommittoday.techswipe.core.controller.servlet.PageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TechContentListQueryController {

    private final TechContentListQueryService techContentListQueryService;

    @GetMapping("/api/content/v1/contents")
    public ResponseEntity<ListResponse<TechContentResponse>> getList(
            final @ModelAttribute @Valid PageRequest pageRequest,
            final @ModelAttribute TechContentListQueryRequest request
    ) {

        final List<TechContentQueryResult> contentList = techContentListQueryService.getList(
                pageRequest.toPageParam(), new TechContentListQueryParam(request.categories())
        );

        return ResponseEntity.ok(new ListResponse<>(
                contentList.stream()
                        .map(TechContentResponse::from)
                        .toList())
        );
    }

    @GetMapping("/api/content/v1/contents-count")
    public ResponseEntity<TechContentCountResponse> count(
            final @ModelAttribute TechContentListQueryRequest request
    ) {
        return ResponseEntity.ok(new TechContentCountResponse(
                techContentListQueryService.count(new TechContentListQueryParam(request.categories()))
        ));
    }

}
