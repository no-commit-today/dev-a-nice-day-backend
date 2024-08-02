package com.nocommittoday.techswipe.domain.content;

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
