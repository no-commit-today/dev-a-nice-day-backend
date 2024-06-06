package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.application.port.in.ContentListQueryParam;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;

import java.util.List;

public interface ContentCategoryFilteredReaderPort {

    List<TechContent> getList(final PageParam pageParam, final ContentListQueryParam queryParam);
}
