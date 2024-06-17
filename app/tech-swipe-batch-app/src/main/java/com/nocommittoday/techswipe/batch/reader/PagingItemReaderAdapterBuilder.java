package com.nocommittoday.techswipe.batch.reader;

import org.springframework.util.Assert;

public class PagingItemReaderAdapterBuilder<T> {

    private PagingItemReadStrategy<T> readStrategy;

    private int pageSize = 10;

    private boolean saveState = true;

    private String name;

    private int maxItemCount = Integer.MAX_VALUE;

    private int currentItemCount;

    public PagingItemReaderAdapterBuilder<T> readStrategy(final PagingItemReadStrategy<T> readStrategy) {
        this.readStrategy = readStrategy;
        return this;
    }

    public PagingItemReaderAdapterBuilder<T> pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PagingItemReaderAdapterBuilder<T> saveState(final boolean saveState) {
        this.saveState = saveState;
        return this;
    }

    public PagingItemReaderAdapterBuilder<T> name(final String name) {
        this.name = name;
        return this;
    }

    public PagingItemReaderAdapterBuilder<T> maxItemCount(final int maxItemCount) {
        this.maxItemCount = maxItemCount;
        return this;
    }

    public PagingItemReaderAdapterBuilder<T> currentItemCount(final int currentItemCount) {
        this.currentItemCount = currentItemCount;
        return this;
    }

    public PagingItemReaderAdapter<T> build() {
        Assert.isTrue(pageSize > 0, "pageSize must be greater than zero");
        Assert.notNull(readStrategy, "An EntityManagerFactory is required");

        if (saveState) {
            Assert.hasText(name, "A name is required when saveState is set to true");
        }

        final PagingItemReaderAdapter<T> reader = new PagingItemReaderAdapter<>(readStrategy);
        reader.setPageSize(pageSize);
        reader.setSaveState(saveState);
        reader.setName(name);
        reader.setMaxItemCount(maxItemCount);
        reader.setCurrentItemCount(currentItemCount);
        return reader;
    }
}
