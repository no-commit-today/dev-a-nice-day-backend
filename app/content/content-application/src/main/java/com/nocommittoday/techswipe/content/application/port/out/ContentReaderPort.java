package com.nocommittoday.techswipe.content.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;

import java.util.List;

public interface ContentReaderPort {

    List<TechContent> getList(final PageParam pageParam);
}
