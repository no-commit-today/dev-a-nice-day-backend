package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.core.domain.vo.PageParam;

import java.util.List;

public interface ContentListQuery {

    List<ContentResult> getList(final PageParam pageParam, final ContentListQueryParam queryParam);
}
