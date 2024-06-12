package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.infrastructure.ContentCategoryFilteredReader;
import com.nocommittoday.techswipe.content.infrastructure.ContentReader;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentListQueryService {

    private final ContentReader contentReader;
    private final ContentCategoryFilteredReader contentCategoryFilteredReader;

    public List<ContentResult> getList(final PageParam pageParam, final ContentListQueryParam queryParam) {
        if (queryParam.categories().isEmpty()) {
            return contentReader.getList(pageParam).stream()
                    .map(ContentResult::from)
                    .toList();
        } else {
            return contentCategoryFilteredReader.getList(pageParam, queryParam.categories()).stream()
                    .map(ContentResult::from)
                    .toList();
        }
    }
}
