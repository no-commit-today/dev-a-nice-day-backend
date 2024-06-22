package com.nocommittoday.techswipe.content.controller;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.service.ContentLinkService;
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

    public static final String CONTENT_LINK_PATH = "/contents/{contentId}/link";
    private final ContentLinkService contentLinkService;

    @GetMapping(CONTENT_LINK_PATH)
    public ResponseEntity<Void> link(@PathVariable final Long contentId) {
        final String url = contentLinkService.link(new TechContent.Id(contentId));

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }
}
