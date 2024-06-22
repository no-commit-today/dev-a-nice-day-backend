package com.nocommittoday.techswipe.content.controller;

import com.nocommittoday.techswipe.content.domain.TechContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ContentLinkCreator {

    private final String baseUrl;

    public ContentLinkCreator(@Value("${app.host}") final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String create(final TechContent.Id contentId) {
        return UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(ContentLinkController.CONTENT_LINK_PATH)
                .build(contentId.value())
                .toString();
    }
}
