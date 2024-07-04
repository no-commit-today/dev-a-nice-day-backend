package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.service.ContentListQueryParam;
import com.nocommittoday.techswipe.content.service.ContentListQueryService;
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
public class ContentListQueryController {

    private final ContentListQueryService contentListQueryService;

    @GetMapping("/api/content/v1/contents")
    public ResponseEntity<ListResponse<ContentResponse>> getList(
            final @ModelAttribute @Valid PageRequest pageRequest,
            final @ModelAttribute ContentListQueryRequest request
    ) {

        final List<TechContentQueryResult> contentList = contentListQueryService.getList(
                pageRequest.toPageParam(), new ContentListQueryParam(request.categories())
        );

        return ResponseEntity.ok(new ListResponse<>(
                contentList.stream()
                        .map(ContentResponse::from)
                        .toList())
        );
    }

    @GetMapping("/api/content/v1/contents-count")
    public ResponseEntity<ContentCountResponse> count(
            final @ModelAttribute ContentListQueryRequest request
    ) {
        return ResponseEntity.ok(new ContentCountResponse(
                contentListQueryService.count(new ContentListQueryParam(request.categories()))
        ));
    }

}
