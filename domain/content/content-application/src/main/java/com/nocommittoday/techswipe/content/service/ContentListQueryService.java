package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.infrastructure.ContentCategorizedListReader;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentListQueryService {

    private final ContentCategorizedListReader contentCategorizedListReader;

    public List<ContentResult> getList(final PageParam pageParam, final ContentListQueryParam queryParam) {
        return contentCategorizedListReader.getList(pageParam, queryParam.categories()).stream()
                .map(ContentResult::from)
                .toList();

    }
}
