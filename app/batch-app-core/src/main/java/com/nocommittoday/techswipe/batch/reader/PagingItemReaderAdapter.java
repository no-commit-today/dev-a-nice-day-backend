package com.nocommittoday.techswipe.batch.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class PagingItemReaderAdapter<T> extends AbstractPagingItemReader<T> {

    private final PagingItemReadStrategy<T> readStrategy;

    private int pageOffset = 0;

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        if (pageOffset < 0) {
            throw new IllegalArgumentException("pageOffset 는 0보다 커야합니다.");
        }
        this.pageOffset = pageOffset;
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        final List<T> result = readStrategy.readPage(getPageOffset() + getPage(), getPageSize());
        results.addAll(result);
    }
}
