package com.nocommittoday.techswipe.domain.content;

import org.springframework.stereotype.Service;

@Service
public class TechContentListQueryServiceNew {

    private final TechContentListQueryReaderNew techContentListQueryReader;

    public TechContentListQueryServiceNew(TechContentListQueryReaderNew techContentListQueryReader) {
        this.techContentListQueryReader = techContentListQueryReader;
    }

    public TechContentListQueryResult getList(TechContentListQueryParamNew param) {
        return new TechContentListQueryResult(
                techContentListQueryReader.getList(param)
        );
    }
}
