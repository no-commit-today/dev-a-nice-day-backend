package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import org.springframework.stereotype.Service;

@Service
public class TechContentListQueryService {

    private final TechContentListQueryReader techContentListQueryReader;
    private final TechContentCountReader techContentCountReader;

    public TechContentListQueryService(
            TechContentListQueryReader techContentListQueryReader,
            TechContentCountReader techContentCountReader
    ) {
        this.techContentListQueryReader = techContentListQueryReader;
        this.techContentCountReader = techContentCountReader;
    }

    public TechContentListQueryResult getList(PageParam pageParam, TechContentListQueryParam queryParam) {
        return new TechContentListQueryResult(
                techContentListQueryReader.getListV1(pageParam, queryParam.categories())
        );
    }

    public long count(TechContentListQueryParam queryParam) {
        return techContentCountReader.count(queryParam.categories());
    }
}
