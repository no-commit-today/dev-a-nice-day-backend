package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TechContentUpdater {

    private final TechContentJpaRepository techContentJpaRepository;

    @Transactional
    public void update(final TechContent techContent) {
        final TechContentEntity entity = techContentJpaRepository.findById(techContent.getId().value())
                .orElseThrow(() -> new TechContentNotFoundException(techContent.getId()));
        entity.update(techContent);
    }
}
