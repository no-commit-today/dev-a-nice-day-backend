package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechContentSwipeQueryService {

    private final TechContentSwipeQueryReader techContentSwipeQueryReader;

    public TechContentSwipeQueryService(TechContentSwipeQueryReader techContentSwipeQueryReader) {
        this.techContentSwipeQueryReader = techContentSwipeQueryReader;
    }

    public List<TechContentSwipeQueryResult> getList(PageParam pageParam, TechContentSwipeQueryParam param) {
        return techContentSwipeQueryReader.getList(pageParam, param.categories());
    }
}
