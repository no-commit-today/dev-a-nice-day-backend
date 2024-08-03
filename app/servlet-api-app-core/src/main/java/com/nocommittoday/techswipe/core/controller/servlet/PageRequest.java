package com.nocommittoday.techswipe.core.controller.servlet;

import com.nocommittoday.techswipe.domain.core.PageParam;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

import javax.annotation.Nullable;
import java.util.Objects;

public class PageRequest {
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    @Positive
    private final int page;

    @Max(MAX_SIZE)
    @Positive
    private final int size;

    public PageRequest(
            @Nullable Integer page, @Nullable Integer size
    ) {
        this.page = page == null ? getDefaultPage() : page;
        this.size = size == null ? getDefaultSize() : size;
    }

    public final int getDefaultPage() {
        return DEFAULT_PAGE;
    }

    public int getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public final long getOffset() {
        return (long) (page - 1) * size;
    }

    public final PageParam toPageParam() {
        return new PageParam(page, size);
    }

    @Positive
    public int getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequest that = (PageRequest) o;
        return page == that.page && size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size);
    }
}
