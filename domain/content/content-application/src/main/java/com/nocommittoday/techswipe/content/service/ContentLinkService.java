package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.infrastructure.ContentUrlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentLinkService {

    private final ContentUrlReader contentUrlReader;

    public String link(final TechContent.Id id) {
        return contentUrlReader.get(id);
    }
}
