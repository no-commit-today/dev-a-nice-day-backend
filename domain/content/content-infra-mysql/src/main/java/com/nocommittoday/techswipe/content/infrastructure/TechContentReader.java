package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContent getIncludingDeleted(final TechContent.Id id) {
        return techContentJpaRepository.findById(id.value())
                .map(TechContentEntity::toDomain)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
