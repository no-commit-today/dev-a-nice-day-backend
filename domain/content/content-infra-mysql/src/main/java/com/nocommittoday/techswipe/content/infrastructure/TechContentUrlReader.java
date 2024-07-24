package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentUrlReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public String get(TechContentId id) {
        return techContentJpaRepository.findUrlByIdAndDeletedIsFalse(id.value())
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
