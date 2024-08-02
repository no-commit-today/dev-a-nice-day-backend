package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.infrastructure.TechContentUrlReader;
import org.springframework.stereotype.Service;

@Service
public class TechContentLinkService {

    private final TechContentUrlReader techContentUrlReader;

    public TechContentLinkService(TechContentUrlReader techContentUrlReader) {
        this.techContentUrlReader = techContentUrlReader;
    }

    public String link(TechContentId id) {
        return techContentUrlReader.get(id);
    }
}
