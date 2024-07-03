package com.nocommittoday.techswipe.collection.domain;

import java.util.Set;

/**
 * INIT
 * ├── CATEGORIZED
 * │   └── SUMMARIZED
 * │       └── PUBLISHED
 * └── FILTERED
 */
public enum CollectionStatus {
    INIT, // 처음 수집된 상태
    CATEGORIZED, // 카테고리 분류된 상태
    FILTERED, // 카테고리 분류되었으나, 다루지 않는 주제인 상태
    SUMMARIZED, // 내용 요약이 완료된 상태
    PUBLISHED // 발행된 상태
    ;

    private static final Set<CollectionStatus> EDITABLE_STATUSES = Set.of(FILTERED, PUBLISHED);

    public boolean isEditable() {
        return EDITABLE_STATUSES.contains(this);
    }
}
