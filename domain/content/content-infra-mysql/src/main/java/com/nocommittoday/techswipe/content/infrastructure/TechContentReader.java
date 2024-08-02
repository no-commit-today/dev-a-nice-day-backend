package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TechContentReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    @Transactional(readOnly = true)
    public TechContent getIncludingDeleted(TechContentId id) {
        return techContentJpaRepository.findById(id.value())
                .map(TechContentEntity::toDomain)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public TechContent get(TechContentId id) {
        return techContentJpaRepository.findById(id.value())
                .filter(TechContentEntity::isUsed)
                .map(TechContentEntity::toDomain)
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
