package com.nocommittoday.techswipe.collection.domain.enums;

/**
 * NONE
 * ├── CATEGORIZED
 * │   └── SUMMARIZED
 * │       └── PUBLISHED
 * └── FILTERED
 */
public enum CollectionStatus {
    NONE, // 처음 수집된 상태
    CATEGORIZED, // 카테고리 분류된 상태
    FILTERED, // 카테고리 분류되었으나, 다루지 않는 주제인 상태
    SUMMARIZED, // 내용 요약이 완료된 상태
    PUBLISHED // 발행된 상태
}
