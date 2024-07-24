package com.nocommittoday.techswipe.content.controller;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.service.TechContentLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentLinkController {

    private final TechContentLinkService techContentLinkService;

    @GetMapping("/contents/{contentId}/link")
    public ResponseEntity<Void> link(@PathVariable final Long contentId) {
        final String url = techContentLinkService.link(new TechContentId(contentId));

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }
}
