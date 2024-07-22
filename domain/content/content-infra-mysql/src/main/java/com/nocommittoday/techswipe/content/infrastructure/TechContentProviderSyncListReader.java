package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderSync;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.core.domain.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TechContentProviderSyncListReader {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    public List<TechContentProviderSync> getList(final PageParam pageParam, final LocalDateTime from, final LocalDateTime to) {
        return techContentProviderJpaRepository.findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThan(
                         PageRequest.of(
                                 pageParam.page() - 1, pageParam.size(),
                                 Sort.by(Sort.Order.desc("lastModifiedAt"))
                         ),
                        from, to
                )
                .stream()
                .map(TechContentProviderEntity::toSync)
                .toList();
    }
}
