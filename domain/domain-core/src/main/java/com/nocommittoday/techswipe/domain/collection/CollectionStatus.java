package com.nocommittoday.techswipe.domain.collection;


public enum CollectionStatus {
    COLLECTED, // 수집된 상태
    INIT, // 수집 후 초기화 완료된 상태
    INIT_FAILED, // 수집 후 초기화에 실패한 상태
    CATEGORIZED, // 카테고리 분류된 상태
    CATEGORIZATION_FAILED, // 카테고리 분류에 실패한 상태
    FILTERED, // 카테고리 분류되었으나, 다루지 않는 주제를 가지는 상태
    SUMMARIZED, // 내용 요약이 완료된 상태
    SUMMARIZATION_FAILED, // 내용 요약에 실패한 상태
    PUBLISHED // 발행된 상태
    ;

    public boolean categorizable() {
        return this == INIT || this == CATEGORIZATION_FAILED;
    }

    public boolean summarizable() {
        return this == CATEGORIZED || this == SUMMARIZATION_FAILED;
    }

    public boolean publishable() {
        return this == SUMMARIZED;
    }

    public CollectionStatus toFailed() {
        return switch (this) {
            case COLLECTED -> INIT_FAILED;
            case INIT -> CATEGORIZATION_FAILED;
            case CATEGORIZED -> SUMMARIZATION_FAILED;
            default -> throw new IllegalStateException("실패 상태가 불가능한 상태. status=" + this);
        };
    }
}
