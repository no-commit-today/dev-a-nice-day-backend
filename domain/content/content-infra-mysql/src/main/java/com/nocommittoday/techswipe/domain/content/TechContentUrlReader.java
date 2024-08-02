package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentUrlReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentUrlReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    public String get(TechContentId id) {
        return techContentJpaRepository.findUrlByIdAndDeletedIsFalse(id.value())
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
