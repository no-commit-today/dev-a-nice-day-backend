package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TechContentListQueryReaderNew {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentListQueryReaderNew(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public List<TechContentQuery> getList(TechContentListQueryParamNew param) {
        return techContentJpaRepository.findAllByGreaterThanContentCategoryInOrderByPublishedDateDescAndDeletedIsFalse(
                        param.id(), param.categories(), param.size()
                ).stream()
                .map(TechContentEntity::toQuery)
                .toList();
    }
}
