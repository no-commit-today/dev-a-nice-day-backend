package com.nocommittoday.techswipe.content.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ContentListQuery;
import com.nocommittoday.techswipe.content.application.port.in.ContentListQueryParam;
import com.nocommittoday.techswipe.content.application.port.in.ContentResult;
import com.nocommittoday.techswipe.content.application.port.out.ContentCategoryFilteredReaderPort;
import com.nocommittoday.techswipe.content.application.port.out.ContentReaderPort;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ContentListQueryService implements ContentListQuery {

    private final ContentReaderPort contentReaderPort;
    private final ContentCategoryFilteredReaderPort contentCategoryFilteredReaderPort;

    @Override
    public List<ContentResult> getList(final PageParam pageParam, final ContentListQueryParam queryParam) {
        if (queryParam.categories().isEmpty()) {
            return contentReaderPort.getList(pageParam).stream()
                    .map(ContentResult::from)
                    .toList();
        } else {
            return contentCategoryFilteredReaderPort.getList(pageParam, queryParam).stream()
                    .map(ContentResult::from)
                    .toList();
        }
    }
}
