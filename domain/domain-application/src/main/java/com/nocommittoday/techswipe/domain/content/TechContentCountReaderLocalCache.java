package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.AbstractLocalCacheReader;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class TechContentCountReaderLocalCache extends AbstractLocalCacheReader<List<TechCategory>, Long> {

    private final TechContentCountReader techContentCountReader;

    public TechContentCountReaderLocalCache(TechContentCountReader techContentCountReader) {
        super(Duration.ofMinutes(30));
        this.techContentCountReader = techContentCountReader;
    }

    @Override
    protected Long loadData(List<TechCategory> key) {
        return techContentCountReader.count(key);
    }
}
