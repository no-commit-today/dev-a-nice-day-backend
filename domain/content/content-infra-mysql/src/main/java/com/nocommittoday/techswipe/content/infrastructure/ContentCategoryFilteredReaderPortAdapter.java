package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.application.port.in.ContentListQueryParam;
import com.nocommittoday.techswipe.content.application.port.out.ContentCategoryFilteredReaderPort;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class ContentCategoryFilteredReaderPortAdapter implements ContentCategoryFilteredReaderPort {

    private final TechContentJpaRepository techContentJpaRepository;

    @NonNull
    @Override
    public List<TechContent> getList(
            @NonNull final PageParam pageParam,
            @NonNull final ContentListQueryParam queryParam
    ) {
        return techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                        PageRequest.of(pageParam.page(), pageParam.size()),
                        queryParam.categories()
                ).stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }
}
