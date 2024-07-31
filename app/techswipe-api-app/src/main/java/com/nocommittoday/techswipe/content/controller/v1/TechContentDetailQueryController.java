package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.service.TechContentDetailQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechContentDetailQueryController {

    private final TechContentDetailQueryService techContentDetailQueryService;

    @GetMapping("/api/content/v1/contents/{contentId}")
    public TechContentDetailQueryResponse getDetail(@PathVariable Long contentId) {
        return TechContentDetailQueryResponse.from(
                techContentDetailQueryService.get(new TechContentId(contentId))
        );
    }
}
