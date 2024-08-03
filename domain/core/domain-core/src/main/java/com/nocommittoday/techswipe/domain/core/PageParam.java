package com.nocommittoday.techswipe.domain.core;

public record PageParam(
        int page,
        int size
) {
    public PageParam {
        if (page < 1) {
            throw new IllegalArgumentException("page 는 0 보다 커야 합니다.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("size 는 0 보다 커야 합니다.");
        }
    }

    public long offset() {
        return (long) (page - 1) * size;
    }
}
