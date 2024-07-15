package com.nocommittoday.techswipe.core.controller.servlet;

import com.nocommittoday.techswipe.core.domain.PageParam;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@ToString
@EqualsAndHashCode
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
            @Nullable final Integer page, @Nullable final Integer size
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
}
