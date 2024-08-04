package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.content.v1.response.TechContentDetailQueryResponse;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechContentDetailQueryController {

    private final TechContentDetailQueryService techContentDetailQueryService;

    public TechContentDetailQueryController(
            TechContentDetailQueryService techContentDetailQueryService
    ) {
        this.techContentDetailQueryService = techContentDetailQueryService;
    }

    @GetMapping("/api/content/v1/contents/{contentId}")
    public TechContentDetailQueryResponse getDetail(@PathVariable Long contentId) {
        return TechContentDetailQueryResponse.from(
                techContentDetailQueryService.get(new TechContentId(contentId))
        );
    }
}
