package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.content.v1.request.TechContentSwipeQueryRequest;
import com.nocommittoday.techswipe.controller.content.v1.response.TechContentSwipeQueryResponse;
import com.nocommittoday.techswipe.controller.core.ListResponse;
import com.nocommittoday.techswipe.controller.core.PageRequest;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryParam;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechContentSwipeQueryController {

    private final TechContentSwipeQueryService techContentSwipeQueryService;

    public TechContentSwipeQueryController(TechContentSwipeQueryService techContentSwipeQueryService) {
        this.techContentSwipeQueryService = techContentSwipeQueryService;
    }

    @GetMapping("/api/content/v1/swipes")
    public ListResponse<TechContentSwipeQueryResponse> getList(
            @ModelAttribute @Valid PageRequest pageRequest,
            @ModelAttribute TechContentSwipeQueryRequest request
    ) {
        return new ListResponse<>(techContentSwipeQueryService.getList(
                        pageRequest.toPageParam(), new TechContentSwipeQueryParam(request.categories()))
                .stream()
                .map(TechContentSwipeQueryResponse::from)
                .toList()
        );
    }
}
