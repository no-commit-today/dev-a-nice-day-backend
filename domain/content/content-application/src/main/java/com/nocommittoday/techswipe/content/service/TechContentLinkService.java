package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUrlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechContentLinkService {

    private final TechContentUrlReader techContentUrlReader;

    public String link(final TechContent.Id id) {
        return techContentUrlReader.get(id);
    }
}
