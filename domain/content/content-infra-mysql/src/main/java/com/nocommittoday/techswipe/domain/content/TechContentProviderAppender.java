package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
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
