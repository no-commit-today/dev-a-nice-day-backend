package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntityMapper;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class TechContentProviderAppender {

    private final TechContentProviderJpaRepository repository;
    private final TechContentProviderEntityMapper mapper;

    public TechContentProviderAppender(
            TechContentProviderJpaRepository repository, TechContentProviderEntityMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TechContentProviderId save(TechContentProviderCreate command) {
        return new TechContentProviderId(
                Objects.requireNonNull(repository.save(mapper.from(command)).getId())
        );
    }
}
