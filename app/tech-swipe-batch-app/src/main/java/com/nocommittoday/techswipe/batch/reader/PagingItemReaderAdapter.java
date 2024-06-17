package com.nocommittoday.techswipe.batch.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class PagingItemReaderAdapter<T> extends AbstractPagingItemReader<T> {

    private final PagingItemReadStrategy<T> readStrategy;

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        final List<T> result = readStrategy.readPage(getPage(), getPageSize());
        results.addAll(result);
    }
}
