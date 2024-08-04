package com.nocommittoday.techswipe.domain.collection;

/**
 * INIT
 * ├── CATEGORIZATION_FAILED
 * │   │
 * ├── CATEGORIZED
 * │   └── SUMMARIZED
 * │       └── PUBLISHED
 * └── FILTERED
 */
public enum CollectionStatus {
    INIT, // 처음 수집된 상태
    CATEGORIZED, // 카테고리 분류된 상태
    CATEGORIZATION_FAILED, // 카테고리 분류에 실패한 상태
    FILTERED, // 카테고리 분류되었으나, 다루지 않는 주제인 상태
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
}