package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUrlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechContentLinkService {

    private final TechContentUrlReader techContentUrlReader;

    public String link(TechContentId id) {
        return techContentUrlReader.get(id);
    }
}
