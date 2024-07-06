package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechContentSyncListReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public List<TechContent> getList(final PageParam pageParam, final LocalDateTime from, final LocalDateTime to) {
        return techContentJpaRepository.findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThanAndDeletedIsFalse(
                         PageRequest.of(
                                 pageParam.page() - 1, pageParam.size(),
                                 Sort.by(Sort.Order.desc("lastModifiedAt"))
                         ),
                        from, to
                )
                .stream()
                .map(TechContentEntity::toDomain)
                .toList();
    }
}
