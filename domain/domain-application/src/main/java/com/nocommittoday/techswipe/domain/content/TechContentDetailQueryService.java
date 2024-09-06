package com.nocommittoday.techswipe.domain.content;

import org.springframework.stereotype.Service;

@Service
public class TechContentDetailQueryService {

    private final TechContentQueryReader techContentQueryReader;

    public TechContentDetailQueryService(TechContentQueryReader techContentQueryReader) {
        this.techContentQueryReader = techContentQueryReader;
    }

    public TechContentDetailQueryResult get(TechContentId id) {
        TechContentQuery techContent = techContentQueryReader.get(id);
        return new TechContentDetailQueryResult(
                techContent
        );
    }
}
