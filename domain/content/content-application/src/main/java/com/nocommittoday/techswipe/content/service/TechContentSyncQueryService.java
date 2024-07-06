package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.infrastructure.TechContentSyncListReader;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechContentSyncQueryService {

    private final TechContentSyncListReader techContentSyncListReader;

    public List<TechContent> getList(
            final PageParam pageParam, final TechContentSyncQueryParam queryParam
    ) {
        return techContentSyncListReader.getList(pageParam, queryParam.from(), queryParam.to());
    }
}
