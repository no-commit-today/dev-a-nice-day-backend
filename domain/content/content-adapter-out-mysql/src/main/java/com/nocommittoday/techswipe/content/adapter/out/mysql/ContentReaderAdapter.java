package com.nocommittoday.techswipe.content.adapter.out.mysql;

import com.nocommittoday.techswipe.content.application.port.out.ContentReaderPort;
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
class ContentReaderAdapter implements ContentReaderPort {

    private final TechContentJpaRepository techContentJpaRepository;

    @NonNull
    @Override
    public List<TechContent> getList(@NonNull final PageParam pageParam) {
        return techContentJpaRepository.findAllWithProviderOrderByPublishedDateDesc(
                PageRequest.of(pageParam.page(), pageParam.size())
        ).stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }
}
